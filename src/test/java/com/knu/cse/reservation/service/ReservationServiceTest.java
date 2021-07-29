package com.knu.cse.reservation.service;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.classroom.service.ClassRoomService;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.comment.domain.Comment;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.member.dto.SignUpForm;
import com.knu.cse.member.model.Gender;
import com.knu.cse.member.model.Major;
import com.knu.cse.member.model.Member;
import com.knu.cse.reservation.domain.FindReservationDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class ReservationServiceTest {

    @Autowired private ReservationService reservationService;
    @Autowired private AuthService authService;
    @Autowired private ClassRoomService classRoomService;

    @Test
    @Rollback(value = false)
    void findReservation_테스트() throws Exception {
        SignUpForm signUpForm1 = new SignUpForm("Girin", "nun2580@knu.ac.kr", "samsung159!", "김기현", "2016113934", Gender.MALE, Major.ADVANCED);
        Member member1 = authService.signUpMember(signUpForm1);
        ClassRoom classRoom = classRoomService.RegistrationClassRoom(Building.IT5, 104L, 20L);
        ClassSeat classSeat = classRoomService.findClassSeatByBuildingAndRoomAndSeatNum(Building.IT5, 104L, 15L);

        reservationService.reservationSeat(member1.getId(),classSeat.getId());
        FindReservationDTO reservation = reservationService.findReservation(member1.getId());

    }
}