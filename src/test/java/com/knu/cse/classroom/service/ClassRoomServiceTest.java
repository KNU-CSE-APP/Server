package com.knu.cse.classroom.service;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.classroom.repository.ClassRoomRepository;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.classseat.domain.Status;
import com.knu.cse.classseat.repository.ClassSeatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClassRoomServiceTest {

    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private ClassSeatRepository classSeatRepository;
    @Autowired
    private ClassRoomService classRoomService;

    @Test
    @Rollback(value = false)
    public void 현재강의실좌석현황(){

        ClassRoom room101 = new ClassRoom(101L, Building.IT5, 20L);
        ClassRoom room104 = new ClassRoom(104L, Building.IT4, 25L);
        classRoomRepository.save(room101);
        classRoomRepository.save(room104);

        ClassSeat classSeat1 = new ClassSeat(1, Status.UNRESERVED,room104);
        ClassSeat classSeat2 = new ClassSeat(2, Status.UNRESERVED,room104);
        ClassSeat classSeat3 = new ClassSeat(3, Status.RESERVED,room104);
        ClassSeat classSeat4 = new ClassSeat(4, Status.RESERVED,room104);

        ClassSeat classSeat5 = new ClassSeat(1, Status.RESERVED,room101);
        ClassSeat classSeat6 = new ClassSeat(2, Status.RESERVED,room101);
        ClassSeat classSeat7 = new ClassSeat(3, Status.UNRESERVED,room101);
        ClassSeat classSeat8 = new ClassSeat(4, Status.UNRESERVED,room101);

        classSeatRepository.save(classSeat1);
        classSeatRepository.save(classSeat2);
        classSeatRepository.save(classSeat3);
        classSeatRepository.save(classSeat4);
        classSeatRepository.save(classSeat5);
        classSeatRepository.save(classSeat6);
        classSeatRepository.save(classSeat7);
        classSeatRepository.save(classSeat8);

        List<ClassRoom> allClassRoomsAndClassSeats = classRoomService.findAllClassRoomsAndClassSeats();
        for (ClassRoom allClassRoomsAndClassSeat : allClassRoomsAndClassSeats) {
            System.out.println("allClassRoomsAndClassSeat.getNumber() = " + allClassRoomsAndClassSeat.getNumber());
            System.out.println("allClassRoomsAndClassSeat.getBuilding() = " + allClassRoomsAndClassSeat.getBuilding());

            for (ClassSeat classSeat : allClassRoomsAndClassSeat.getClassSeats()) {
                System.out.println("classSeat.getNumber() = " + classSeat.getNumber());
                System.out.println("classSeat.getStatus() = " + classSeat.getStatus());
            }
        }
    }

    @Test
    public void 강의실과좌석번호를통한_좌석상태Test(){
        ClassRoom room101 = new ClassRoom(101L, Building.IT5, 20L);
        ClassRoom room104 = new ClassRoom(104L, Building.IT4, 25L);
        classRoomRepository.save(room101);
        classRoomRepository.save(room104);

        ClassSeat classSeat1 = new ClassSeat(1, Status.UNRESERVED,room104);
        ClassSeat classSeat2 = new ClassSeat(2, Status.UNRESERVED,room104);
        ClassSeat classSeat3 = new ClassSeat(3, Status.RESERVED,room104);
        ClassSeat classSeat4 = new ClassSeat(4, Status.RESERVED,room104);

        ClassSeat classSeat5 = new ClassSeat(1, Status.RESERVED,room101);
        ClassSeat classSeat6 = new ClassSeat(2, Status.RESERVED,room101);
        ClassSeat classSeat7 = new ClassSeat(3, Status.UNRESERVED,room101);
        ClassSeat classSeat8 = new ClassSeat(4, Status.UNRESERVED,room101);

        classSeatRepository.save(classSeat1);
        classSeatRepository.save(classSeat2);
        classSeatRepository.save(classSeat3);
        classSeatRepository.save(classSeat4);
        classSeatRepository.save(classSeat5);
        classSeatRepository.save(classSeat6);
        classSeatRepository.save(classSeat7);
        classSeatRepository.save(classSeat8);

        ClassSeat seat = classRoomService.findClassSeatByBuildingAndRoomAndSeatNum(Building.IT4, 104L, 3);
        System.out.println("seat.toString() = " + seat.toString());
    }


    @Test
    @Rollback(value = false)
    public void 해당건물의방에서_예약가능한_모든좌석찾기(){

        ClassRoom room101 = new ClassRoom(101L, Building.IT5, 20L);
        ClassRoom room104 = new ClassRoom(104L, Building.IT4, 25L);
        classRoomRepository.save(room101);
        classRoomRepository.save(room104);

        ClassSeat classSeat1 = new ClassSeat(1, Status.UNRESERVED,room104);
        ClassSeat classSeat2 = new ClassSeat(2, Status.UNRESERVED,room104);
        ClassSeat classSeat3 = new ClassSeat(3, Status.RESERVED,room104);
        ClassSeat classSeat4 = new ClassSeat(4, Status.RESERVED,room104);

        ClassSeat classSeat5 = new ClassSeat(1, Status.RESERVED,room101);
        ClassSeat classSeat6 = new ClassSeat(2, Status.RESERVED,room101);
        ClassSeat classSeat7 = new ClassSeat(3, Status.UNRESERVED,room101);
        ClassSeat classSeat8 = new ClassSeat(4, Status.UNRESERVED,room101);

        classSeatRepository.save(classSeat1);
        classSeatRepository.save(classSeat2);
        classSeatRepository.save(classSeat3);
        classSeatRepository.save(classSeat4);
        classSeatRepository.save(classSeat5);
        classSeatRepository.save(classSeat6);
        classSeatRepository.save(classSeat7);
        classSeatRepository.save(classSeat8);

        List<ClassSeat> unreservedClassSeat = classRoomService.findUnreservedClassSeat(Building.IT5,101L);
        for (ClassSeat classSeat : unreservedClassSeat) {
            System.out.println("classSeat = " + classSeat);
            assertThat(classSeat.getStatus()).isEqualTo(Status.UNRESERVED);
        }
    }

    @Test
    @Rollback(value = false)
    public void 해당건물의방에서_예약불가능한_모든좌석찾기(){

        ClassRoom room101 = new ClassRoom(101L, Building.IT5, 20L);
        ClassRoom room104 = new ClassRoom(104L, Building.IT4, 25L);
        classRoomRepository.save(room101);
        classRoomRepository.save(room104);

        ClassSeat classSeat1 = new ClassSeat(1, Status.UNRESERVED,room104);
        ClassSeat classSeat2 = new ClassSeat(2, Status.UNRESERVED,room104);
        ClassSeat classSeat3 = new ClassSeat(3, Status.RESERVED,room104);
        ClassSeat classSeat4 = new ClassSeat(4, Status.RESERVED,room104);

        ClassSeat classSeat5 = new ClassSeat(1, Status.RESERVED,room101);
        ClassSeat classSeat6 = new ClassSeat(2, Status.RESERVED,room101);
        ClassSeat classSeat7 = new ClassSeat(3, Status.UNRESERVED,room101);
        ClassSeat classSeat8 = new ClassSeat(4, Status.UNRESERVED,room101);

        classSeatRepository.save(classSeat1);
        classSeatRepository.save(classSeat2);
        classSeatRepository.save(classSeat3);
        classSeatRepository.save(classSeat4);
        classSeatRepository.save(classSeat5);
        classSeatRepository.save(classSeat6);
        classSeatRepository.save(classSeat7);
        classSeatRepository.save(classSeat8);

        List<ClassSeat> reservedClassSeat = classRoomService.findReservedClassSeat(Building.IT5, 101L);
        for (ClassSeat classSeat : reservedClassSeat) {
            System.out.println("classSeat = " + classSeat);
            assertThat(classSeat.getStatus()).isEqualTo(Status.RESERVED);
        }
    }

}