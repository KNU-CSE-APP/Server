package com.knu.cse.classroom.service;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.classroom.repository.ClassRoomRepository;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.classseat.domain.ClassSeatDTO;
import com.knu.cse.classseat.domain.Status;
import com.knu.cse.classseat.repository.ClassSeatRepository;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.dto.SignUpForm;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.model.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;
    private final ClassSeatRepository classSeatRepository;

    public List<ClassSeatDTO> LookupClassRoom(Long number, Building building){
        ClassRoom classRoom = classRoomRepository.findClassRoomByNumberAndBuilding(number, building).orElseThrow(() ->
                new NotFoundException("해당 강의실이 존재하지 않습니다."));
        List<ClassSeatDTO> classSeats = new ArrayList<ClassSeatDTO>();
        for (ClassSeat classSeat : classRoom.getClassSeats()) {
            Long seatNumber = classSeat.getNumber();
            Status status = classSeat.getStatus();
            classSeats.add(new ClassSeatDTO(seatNumber, status));
        }
        return classSeats;
    }

    /**
     * 강의실 및 좌석 등록하기
     */
    public ClassRoom RegistrationClassRoom(Building building,Long roomNumber,Long totalSeat) throws NotFoundException {
        ClassRoom classRoom = ClassRoom.builder()
                .number(roomNumber)
                .building(building)
                .totalSeats(totalSeat)
                .build();
        if(classRoomRepository.existsByBuildingAndNumber(building, roomNumber)){
            throw new IllegalStateException("이미 강의실이 존재합니다,");
        }
        ClassRoom saveClassRoom = classRoomRepository.save(classRoom);
        for(Long i=1L; i<=totalSeat;i++){
            ClassSeat classSeat = ClassSeat.builder()
                    .number(i)
                    .status(Status.UNRESERVED)
                    .build();
            classSeat.setClassRoom(saveClassRoom);

            classSeatRepository.save(classSeat);
        }
        return saveClassRoom;
    }

    /**
     * 건물안 강의실안 특정 좌석 정보 찾기
     * @param building  : 건물
     * @param roomNumber: 강의실번호
     * @param SeatNumber: 좌석번호
     * @return 좌석 정보, 없다면 null
     */
    public ClassSeat findClassSeatByBuildingAndRoomAndSeatNum(Building building, Long roomNumber, Long SeatNumber){
        ClassRoom findRoom = classRoomRepository.findClassRoomByNumberAndBuilding(roomNumber, building).orElseThrow(()->
            new NotFoundException("해당 강의실이 존재하지 않습니다.")
        );

        List<ClassSeat> classSeats = findRoom.getClassSeats();
        for (ClassSeat classSeat : classSeats) {
            if (classSeat.getNumber().equals(SeatNumber)){
                return classSeat;
            }
        }

        //예외
        throw new NotFoundException("강의실은 있지만, 좌석이 존재하지 않습니다.");
    }

    /**
     * 모든 강의실과 그 안의 좌석 정보 찾기
     * @return 강의실 List
     */
    public List<ClassRoom> findAllClassRoomsAndClassSeats(){
        return (List<ClassRoom>) classRoomRepository.findClassRoomAndClassSeats();
    }

    /**
     * 해당 건물 해당 방의 예약 가능한 자리 찾기
     * @param building   : 찾고자하는 건물번호
     * @param roomNumber : 찾고자하는 강의실 번호
     * @return  : 해당 건물의 해당 강의실 번호에서 예약가능한(Unreserved) 좌석 List
     */
    public List<ClassSeat> findUnreservedClassSeat(Building building, Long roomNumber){
        return classRoomRepository.findClassRoomAndClassSeatsWithUnReserved(building, roomNumber, Status.UNRESERVED);
    }

    /**
     * 해당 건물 해당 방의 예약 불가능한 자리 찾기
     * @param building   : 찾고자하는 건물번호
     * @param roomNumber : 찾고자하는 강의실 번호
     * @return  : 해당 건물의 해당 강의실 번호에서 예약가능한(Unreserved) 좌석 List
     */
    public List<ClassSeat> findReservedClassSeat(Building building, Long roomNumber){
        return classRoomRepository.findClassRoomAndClassSeatsWithUnReserved(building, roomNumber, Status.RESERVED);
    }


    @Transactional
    public ClassRoom classRoom(Building building, Long roomNumber, Long totalSeats) throws IllegalStateException {

        if(!isAlreadyCreatedRoom(building, roomNumber)){
            throw new IllegalStateException("이미 만들어진 강의실 입니다.");
        }

        ClassRoom classRoom = new ClassRoom(roomNumber, building, totalSeats);

        return classRoomRepository.save(classRoom);
    }

    private boolean isAlreadyCreatedRoom(Building building, Long roomNumber) {
        return classRoomRepository.existsByBuildingAndNumber(building,roomNumber);
    }

}
