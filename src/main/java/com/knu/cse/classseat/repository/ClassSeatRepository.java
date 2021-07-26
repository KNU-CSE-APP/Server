package com.knu.cse.classseat.repository;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.classseat.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClassSeatRepository extends JpaRepository<ClassSeat,Long> {

    @Query("select distinct cs from ClassSeat cs " +
            "where cs.classRoom.building = :building " +
            "and cs.classRoom.number = :roomNumber " +
            "and cs.number = :seatNumber")
    ClassSeat findClassSeatByBuildingAndRoomNumber(@Param("building") Building building, @Param("roomNumber") Long roomNumber, @Param("seatNumber") Long seatNumber);

//    @Query("select distinct cs from ClassSeat cs " +
//            "where cs.classRoom.building = :building " +
//            "and cs.classRoom.number = :roomNumber " +
//            "and cs.status = :status")
//    List<ClassSeat> findClassRoomAndClassSeatsWithReserved(@Param("building") Building building, @Param("roomNumber") Long roomNumber, @Param("status") Status status);
}