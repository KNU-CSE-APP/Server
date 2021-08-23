//package com.knu.cse.classroom.service;
//
//import com.knu.cse.board.repository.BoardRepository;
//import com.knu.cse.classroom.domain.Building;
//import com.knu.cse.classroom.domain.ClassRoom;
//import com.knu.cse.classroom.dto.FindClassRoomDTO;
//import com.knu.cse.classseat.domain.ClassSeat;
//import com.knu.cse.comment.service.CommentService;
//import com.knu.cse.email.service.AuthService;
//import com.knu.cse.member.model.Gender;
//import com.knu.cse.member.model.Major;
//import com.knu.cse.member.model.Member;
//import com.knu.cse.member.model.MemberRole;
//import com.knu.cse.member.repository.MemberRepository;
//import com.knu.cse.member.service.MemberService;
//import com.knu.cse.reservation.domain.ReservationDTO;
//import com.knu.cse.reservation.service.ReservationService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//class ClassRoomServiceTest {
//
//    @Autowired private ClassRoomService classRoomService;
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private ReservationService reservationService;
//    @Autowired private PasswordEncoder passwordEncoder;
//
//    @Test
//    @Rollback(value = false)
//    public void db_init() throws Exception{
//        //                .password(passwordEncoder.encode("samsung159!"))
//        Member member1 = Member.builder()
//            .email("nun2580@knu.ac.kr")
//            .password(passwordEncoder.encode("samsung159!)"))
//            .username("김기현")
//            .nickname("Girin")
//            .studentId("2016113934")
//            .gender(Gender.MALE)
//            .major(Major.ADVANCED)
//            .role(MemberRole.ROLE_NOT_PERMITTED)
//            .build();
//
//        Member member2 = Member.builder()
//                .email("nun6801@knu.ac.kr")
//                .password(passwordEncoder.encode("samsung159!"))
//                .username("바보")
//                .nickname("hihi")
//                .studentId("2016111111")
//                .gender(Gender.MALE)
//                .major(Major.ADVANCED)
//                .role(MemberRole.ROLE_NOT_PERMITTED)
//                .build();
//
//        Member member3 = Member.builder()
//                .email("nun1234@knu.ac.kr")
//                .password(passwordEncoder.encode("tjdrhkd159!"))
//                .username("굿굿")
//                .nickname("hihi")
//                .studentId("2016121111")
//                .gender(Gender.MALE)
//                .major(Major.ADVANCED)
//                .role(MemberRole.ROLE_NOT_PERMITTED)
//                .build();
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//        memberRepository.save(member3);
//
//        ClassRoom classRoom1 = classRoomService.RegistrationClassRoom(Building.IT5, 104L, 20L);
//        ClassRoom classRoom2 = classRoomService.RegistrationClassRoom(Building.IT5, 101L, 40L);
//        ClassRoom classRoom3 = classRoomService.RegistrationClassRoom(Building.IT4, 108L, 30L);
//    }
//
//    @Test
//    @Rollback(value = true)
//    public void 전체강의실목록_Test() throws Exception{
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
//        Member member2 = Member.builder()
//                .email("nun6801@knu.ac.kr")
//                .password("samsung159!")
//                .username("바보")
//                .nickname("hihi")
//                .studentId("2016111111")
//                .gender(Gender.MALE)
//                .major(Major.ADVANCED)
//                .role(MemberRole.ROLE_NOT_PERMITTED)
//                .build();
//
//        Member member3 = Member.builder()
//                .email("nun1234@knu.ac.kr")
//                .password("samsung159!")
//                .username("굿굿")
//                .nickname("hihi")
//                .studentId("2016121111")
//                .gender(Gender.MALE)
//                .major(Major.ADVANCED)
//                .role(MemberRole.ROLE_NOT_PERMITTED)
//                .build();
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//        memberRepository.save(member3);
//
//        ClassRoom classRoom1 = classRoomService.RegistrationClassRoom(Building.IT5, 104L, 20L);
//        ClassRoom classRoom2 = classRoomService.RegistrationClassRoom(Building.IT5, 101L, 40L);
//        ClassRoom classRoom3 = classRoomService.RegistrationClassRoom(Building.IT4, 108L, 30L);
//
//        ReservationDTO reservationDTO1 = new ReservationDTO(Building.IT5, 104L, 10L);
//        reservationService.reservationSeat(member1.getId(), reservationDTO1);
//        ReservationDTO reservationDTO2 = new ReservationDTO(Building.IT5, 104L, 1L);
//        reservationService.reservationSeat(member2.getId(), reservationDTO2);
//        ReservationDTO reservationDTO3 = new ReservationDTO(Building.IT4, 108L, 5L);
//        reservationService.reservationSeat(member3.getId(), reservationDTO3);
//
//        ArrayList<FindClassRoomDTO> allClassRoomsAndClassSeats = classRoomService.findAllClassRoomsAndClassSeats();
//        for (FindClassRoomDTO allClassRoomsAndClassSeat : allClassRoomsAndClassSeats) {
//            System.out.println("allClassRoomsAndClassSeat.toString() = " + allClassRoomsAndClassSeat.toString());
//        }
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 강의실중복예약_Test() throws Exception{
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
//        Member member2 = Member.builder()
//                .email("nun6801@knu.ac.kr")
//                .password("samsung159!")
//                .username("바보")
//                .nickname("hihi")
//                .studentId("2016111111")
//                .gender(Gender.MALE)
//                .major(Major.ADVANCED)
//                .role(MemberRole.ROLE_NOT_PERMITTED)
//                .build();
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        ClassRoom classRoom = classRoomService.RegistrationClassRoom(Building.IT5, 104L, 20L);
//        ClassSeat classSeat = classRoomService.findClassSeatByBuildingAndRoomAndSeatNum(Building.IT5, 104L, 10L);
//
//        ReservationDTO reservationDTO = new ReservationDTO(Building.IT5, 104L, 10L);
//
//        Integer num = reservationService.numbersOfPeople(reservationDTO);
//        System.out.println("num = " + num);
//
//        reservationService.reservationSeat(member1.getId(), reservationDTO);
//        Integer num2 = reservationService.numbersOfPeople(reservationDTO);
//        System.out.println("num2 = " + num2);
//
//        ReservationDTO reservationDTO2 = new ReservationDTO(Building.IT5, 104L, 5L);
//
//        reservationService.reservationSeat(member2.getId(), reservationDTO2);
//        Integer num3 = reservationService.numbersOfPeople(reservationDTO2);
//        System.out.println("num3 = " + num3);
//
//
//
//
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 강의실중복예약_Test() throws Exception{
//        Member member1 = Member.builder()
//        .email("nun2580@knu.ac.kr")
//        .password("samsung159!")
//        .username("김기현")
//        .nickname("Girin")
//        .studentId("2016113934")
//        .gender(Gender.MALE)
//        .major(Major.ADVANCED)
//        .role(MemberRole.ROLE_NOT_PERMITTED)
//        .build();
//
//        Member member2 = Member.builder()
//            .email("nun6801@knu.ac.kr")
//            .password("samsung159!")
//            .username("바보")
//            .nickname("hihi")
//            .studentId("2016111111")
//            .gender(Gender.MALE)
//            .major(Major.ADVANCED)
//            .role(MemberRole.ROLE_NOT_PERMITTED)
//            .build();
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        ClassRoom classRoom = classRoomService.RegistrationClassRoom(Building.IT5, 104L, 20L);
//        ClassSeat classSeat = classRoomService.findClassSeatByBuildingAndRoomAndSeatNum(Building.IT5, 104L, 10L);
//
//        reservationService.reservationSeat(member1.getId(),classSeat.getId());
//        System.out.println("classSeat.getStatus() = " + classSeat.getStatus());
//        reservationService.reservationSeat(member2.getId(),classSeat.getId());
//
//    }
//
//    @Test
//    @Rollback(value=false)
//    public void 강의실_삭제_Text() throws Exception {
//        Member member1 = Member.builder()
//        .email("nun2580@knu.ac.kr")
//        .password("samsung159!")
//        .username("김기현")
//        .nickname("Girin")
//        .studentId("2016113934")
//        .gender(Gender.MALE)
//        .major(Major.ADVANCED)
//        .role(MemberRole.ROLE_NOT_PERMITTED)
//        .build();
//
//        memberRepository.save(member1);
//
//        ClassRoom classRoom = classRoomService.RegistrationClassRoom(Building.IT5, 104L, 20L);
//
//        System.out.println("classRoom.toString() = " + classRoom.toString());
//        ClassSeat classSeat = classRoomService.findClassSeatByBuildingAndRoomAndSeatNum(Building.IT5, 104L, 5L);
//
//        reservationService.reservationSeat(member1.getId(),classSeat.getId());
//
//        classRoomService.DeleteClassRoom(Building.IT5,104L);
//
//
//
//    }

//}