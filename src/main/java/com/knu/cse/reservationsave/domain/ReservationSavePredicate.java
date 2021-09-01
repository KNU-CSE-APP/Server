package com.knu.cse.reservationsave.domain;

import com.knu.cse.classroom.domain.Building;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

public class ReservationSavePredicate {

    public static Predicate search(HashMap<String, String> paramMap){
        QReservationSave reservationSave = QReservationSave.reservationSave;

        BooleanBuilder builder = new BooleanBuilder();
        if(!paramMap.get("searchBuilding").equals("")) builder.and(reservationSave.building.eq(Building.valueOf(paramMap.get("searchBuilding"))));
        if(!paramMap.get("searchRoom").equals("")) builder.and(reservationSave.roomNumber.eq(Long.parseLong(paramMap.get("searchRoom"))));
        if(!paramMap.get("searchSeat").equals("")) builder.and(reservationSave.seatNumber.eq(Long.parseLong(paramMap.get("searchSeat"))));
        if(!paramMap.get("searchEmail").equals("")) builder.and(reservationSave.email.eq(paramMap.get("searchEmail")));
        if(!paramMap.get("searchName").equals("")) builder.and(reservationSave.username.eq(paramMap.get("searchName")));
        if(!paramMap.get("searchStudentId").equals("")) builder.and(reservationSave.studentId.eq(paramMap.get("searchStudentId")));
        if(!paramMap.get("currentDate").equals("")){
            LocalDate localDate = LocalDate.parse(paramMap.get("currentDate"));
            builder.and(reservationSave.startTime.between(localDate.atStartOfDay(), localDate.atTime(LocalTime.MAX)));
        }

        return builder;

    }
}
