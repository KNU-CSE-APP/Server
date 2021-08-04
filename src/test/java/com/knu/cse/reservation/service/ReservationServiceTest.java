package com.knu.cse.reservation.service;

import com.knu.cse.board.repository.BoardRepository;
import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.classroom.service.ClassRoomService;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.comment.service.CommentService;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.member.dto.SignUpForm;
import com.knu.cse.member.model.Gender;
import com.knu.cse.member.model.Major;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.model.MemberRole;
import com.knu.cse.member.repository.MemberRepository;
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

//    @Autowired private AuthService authService;
//    @Autowired private CommentService commentService;
//    @Autowired private BoardRepository boardRepository;
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private ClassRoomService classRoomService;
//    @Autowired private ReservationService reservationService;
//
//    @Test
//    @Rollback(value=false)
//    public void 예약기록확인() throws Exception {
//        Member member1 = Member.builder()
//                .email("nun2580@knu.ac.kr")
//                .password("samsung159!")
//                .username("김기현")
//                .nickname("Girin")
//                .studentId("2016113934")
//                .gender(Gender.MALE)
//                .major(Major.ADVANCED)
//                .role(MemberRole.ROLE_NOT_PERMITTED)
//                .build();
//
//        memberRepository.save(member1);
//
//        ClassRoom classRoom = classRoomService.RegistrationClassRoom(Building.IT5, 104L, 20L);
//        ClassSeat classSeat = classRoomService.findClassSeatByBuildingAndRoomAndSeatNum(Building.IT5, 104L, 13L);
//        reservationService.reservationSeat(member1.getId(),classSeat.getId());
//        System.out.println("member1.getReservation() = " + member1.getReservation());
//        System.out.println("member1.getReservation().getClassSeat().getNumber() = " + member1.getReservation().getClassSeat().getNumber());




//    }

}