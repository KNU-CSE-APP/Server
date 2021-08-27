package com.knu.cse.reservation.service;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.classroom.service.ClassRoomService;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.classseat.domain.Status;
import com.knu.cse.classseat.repository.ClassSeatRepository;
import com.knu.cse.email.util.RedisUtil;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.repository.MemberRepository;
import com.knu.cse.reservation.domain.FindReservationDTO;
import com.knu.cse.reservation.domain.Reservation;
import com.knu.cse.reservation.domain.ReservationDTO;
import com.knu.cse.reservation.repository.ReservationRepository;
import java.time.LocalDateTime;

import com.knu.cse.reservationsave.domain.ReservationSave;
import com.knu.cse.reservationsave.repository.ReservationSaveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final ClassSeatRepository classSeatRepository;
    private final ClassRoomService classRoomService;
    private final RedisUtil redisUtil;
    private final ReservationSaveRepository reservationSaveRepository;


    /**
     * 좌석 반납
     */
    @Transactional
    public String unreserved(Long memId) throws NotFoundException{
        Member member = memberRepository.findById(memId).orElseThrow(()->
            new NotFoundException("회원이 존재하지 않습니다.")
        );

        Reservation reservation = reservationRepository.findByMemberId(memId).orElseThrow(()->
            new NotFoundException("예약이 존재하지 않습니다.")
        );

        ClassSeat classSeat = reservation.getClassSeat();
        ClassRoom classRoom = classSeat.getClassRoom();
        classSeat.changeUnReserved();
        reservationRepository.save(reservation);

        reservationSaveRepository.save(
                ReservationSave.builder()
                .building(classRoom.getBuilding())
                .roomNumber(classRoom.getNumber())
                .seatNumber(classSeat.getNumber())
                .member(member)
                .startTime(LocalDateTime.now())
                .returnCheck(Boolean.TRUE)
                .build()
        );

        reservationRepository.deleteByMemberId(member.getId());
        redisUtil.deleteData(String.format("reservation,%s",reservation.getId().toString()));

        return "좌석을 반납하였습니다.";
    }

    /**
     * reservation PK로 자리 반납(자동반납에 사용됨)
     * @param reservationId
     * @return
     * @throws NotFoundException
     */
    @Transactional
    public String unreservedById(Long reservationId) throws NotFoundException{

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new NotFoundException("예약이 존재하지 않습니다.")
        );

        ClassSeat classSeat = reservation.getClassSeat();
        ClassRoom classRoom = classSeat.getClassRoom();
        classSeat.changeUnReserved();
        reservationRepository.save(reservation);

        reservationSaveRepository.save(
                ReservationSave.builder()
                        .building(classRoom.getBuilding())
                        .roomNumber(classRoom.getNumber())
                        .seatNumber(classSeat.getNumber())
                        .member(reservation.getMember())
                        .startTime(LocalDateTime.now())
                        .returnCheck(Boolean.TRUE)
                        .build()
        );

        reservationRepository.delete(reservation);
        return "좌석이 자동 반납되었습니다.";
    }

    /**
     * member가 좌석 예약하기
     * @param memberId
     * @param reservationDTO
     */
    @Transactional
    public String reservationSeat(Long memberId, ReservationDTO reservationDTO) throws Exception {
        if (reservationRepository.existsByMemberId(memberId)){
            throw new IllegalStateException("이미 예약한 좌석이 있습니다.");
        }

        //엔티티 조회
        Member findMember = memberRepository.findById(memberId).orElseThrow(()->
            new NotFoundException("존재하지 않는 회원입니다."));

        ClassSeat findSeat = classSeatRepository.findClassSeat(reservationDTO.getBuilding(), reservationDTO.getRoomNumber(), reservationDTO.getSeatNumber()).orElseThrow(() ->
            new NotFoundException("좌석이 존재하지 않습니다."));

        if(findSeat.getStatus().equals(Status.RESERVED))
            throw new IllegalAccessException("이미 예약된 좌석입니다.");

        if(findSeat.getStatus() == Status.RESERVED){
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }


        //Reservation 정보 생성
        Reservation reservation = Reservation.createReservation(findMember, findSeat);

        reservationRepository.save(reservation);

        reservationSaveRepository.save(
                ReservationSave.builder()
                        .building(reservationDTO.getBuilding())
                        .roomNumber(reservationDTO.getRoomNumber())
                        .seatNumber(reservationDTO.getSeatNumber())
                        .member(reservation.getMember())
                        .startTime(LocalDateTime.now())
                        .returnCheck(Boolean.FALSE)
                        .build()
        );

        redisUtil.setDataExpire(String.format("reservation,%s",reservation.getId().toString()),memberId.toString(),60*60*6L);
        return "자리 예약에 성공했습니다.";
    }

    /**
     * member가 현재 좌석 연장하기
     * @param memberId
     */
    @Transactional
    public FindReservationDTO extensionSeat(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new NotFoundException("등록된 회원이 아닙니다."));

        if (!reservationRepository.existsByMemberId(memberId)) {
            throw new NotFoundException("예약된 자리가 없습니다.");
        }

        Reservation reservation = reservationRepository.findByMemberId(memberId)
            .orElseThrow(() -> new NotFoundException("예약이 존재하지 않습니다."));

        LocalDateTime canExtensionTime = reservation.getDueDate().minusHours(2);
        if(LocalDateTime.now().isBefore(canExtensionTime))
            throw new IllegalAccessException("만료 시각 2시간 전부터 연장이 가능합니다.");

        Long extensionNum = reservation.getExtensionNum();
        if (extensionNum < 3) {
            reservation.upExtensionNum();
            reservation.updateTime();
            redisUtil.setDataExpire(String.format("reservation,%s",reservation.getId().toString()),memberId.toString(),60*60*6L);
            return new FindReservationDTO(member.getReservation());
        } else {
            throw new IllegalStateException("연장 횟수를 초과 했습니다.");
        }
    }

    @Transactional(readOnly = true)
    public FindReservationDTO findReservation(Long memberId) throws NotFoundException{
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
            new NotFoundException("등록된 회원이 아닙니다."));

        if (member.getReservation() == null){
            throw new NotFoundException("예약된 좌석을 찾을 수 없습니다.");
        }

        return new FindReservationDTO(member.getReservation());
    }

    @Transactional
    public void deleteAllReservation(){
        List<Reservation> allReservation = reservationRepository.findAll();

        for (Reservation reservation : allReservation) {
            reservation.getClassSeat().changeUnReserved();
        }
        reservationRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public Integer numbersOfPeople(Building building, Long roomNumber){
        List<ClassSeat> reservedClassSeat = classRoomService.findReservedClassSeat(building, roomNumber);
        return reservedClassSeat.size();
    }

    @Transactional(readOnly = true)
    public Long numberOfExtension(Long memberId) throws Exception{
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new NotFoundException("등록된 회원이 아닙니다."));
        if (member.getReservation() == null){
            throw new NotFoundException("예약을 한 상태가 아닙니다.");
        }

        return member.getReservation().getExtensionNum();
    }
}
