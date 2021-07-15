package com.knu.cse.classroom.repository;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.classseat.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {

    @Query("select distinct cr from ClassRoom cr join fetch cr.classSeats")
    List<ClassRoom> findClassRoomAndClassSeats();

    ClassRoom findClassRoomByNumberAndBuilding(Long number, Building building);

    @Query("select distinct cs from ClassSeat cs " +
            "where cs.classRoom.building = :building " +
            "and cs.classRoom.number = :roomNumber " +
            "and cs.status = :status")
    List<ClassSeat> findClassRoomAndClassSeatsWithUnReserved(@Param("building") Building building, @Param("roomNumber") Long roomNumber, @Param("status")Status status);

    @Query("select distinct cs from ClassSeat cs " +
            "where cs.classRoom.building = :building " +
            "and cs.classRoom.number = :roomNumber " +
            "and cs.status = :status")
    List<ClassSeat> findClassRoomAndClassSeatsWithReserved(@Param("building") Building building, @Param("roomNumber") Long roomNumber, @Param("status")Status status);

}