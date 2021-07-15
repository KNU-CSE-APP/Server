package com.knu.cse.classroom.service;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.classroom.repository.ClassRoomRepository;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.classseat.domain.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;

    public ClassRoom LookupClassRoom(Long number, Building building){
        return classRoomRepository.findClassRoomByNumberAndBuilding(number, building);
    }

    /**
     * 건물안 강의실안 특정 좌석 정보 찾기
     * @param building  : 건물
     * @param roomNumber: 강의실번호
     * @param SeatNumber: 좌석번호
     * @return 좌석 정보, 없다면 null
     */
    public ClassSeat findClassSeatByBuildingAndRoomAndSeatNum(Building building, Long roomNumber, Integer SeatNumber){
        ClassRoom findRoom = classRoomRepository.findClassRoomByNumberAndBuilding(roomNumber, building);

        List<ClassSeat> classSeats = findRoom.getClassSeats();
        for (ClassSeat classSeat : classSeats) {
            if (classSeat.getNumber().equals(SeatNumber)){
                return classSeat;
            }
        }

        //예외
        return null;
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

}
