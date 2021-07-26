package com.knu.cse.reservation.service;

import com.knu.cse.classroom.repository.ClassRoomRepository;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.classseat.domain.Status;
import com.knu.cse.classseat.repository.ClassSeatRepository;
import com.knu.cse.member.dto.SignUpForm;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.model.MemberRole;
import com.knu.cse.member.repository.MemberRepository;
import com.knu.cse.reservation.domain.Reservation;
import com.knu.cse.reservation.repository.ReservationRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private  final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final ClassRoomRepository classRoomRepository;
    private final ClassSeatRepository classSeatRepository;


    /**
     * 좌석 반납
     */
    @Transactional
    public void unreserved(String email){
        Member member = memberRepository.findByEmail(email);

        Reservation reservation = reservationRepository.findByMemberId(member.getId());
        reservation.getClassSeat().changeUnReserved();
        reservationRepository.save(reservation);

        reservationRepository.deleteByMemberId(member.getId());
    }

    /**
     * member가 좌석 예약하기
     * @param memberId
     * @param seatId
     */
    @Transactional
    public void reservationSeat(Long memberId, Long seatId) throws Exception {

        if (reservationRepository.existsByMemberId(memberId)){
            throw new Exception("이미 자리예약을 했습니다.");
        }

        //엔티티 조회
        Optional<Member> findMember = memberRepository.findById(memberId);
        Optional<ClassSeat> findSeat = classSeatRepository.findById(seatId);

        //Reservation 정보 생성
        Reservation reservation = Reservation.createReservation(findMember.get(), findSeat.get());

        reservationRepository.save(reservation);
    }

    /**
     * member가 현재 좌석 연장하기
     * @param memberId
     */
    @Transactional
    public Long extensionSeat(Long memberId){

        Optional<Member> findMember = memberRepository.findById(memberId);

        try{
            //있으면
            if (reservationRepository.existsByMemberId(memberId)) {
                Reservation byMemberId = reservationRepository.findByMemberId(memberId);
                Long extensionNum = byMemberId.getExtensionNum();

                if (extensionNum < 3) {
                    byMemberId.upExtensionNum();
                    byMemberId.updateTime();
                    return byMemberId.getExtensionNum();
                }//없으면
                else{
                    return -1L;
                }
            }
        }catch (Exception e){
            return -1L;
        }
        return -1L;
    }


}
