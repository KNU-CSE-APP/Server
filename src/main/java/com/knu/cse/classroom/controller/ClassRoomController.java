package com.knu.cse.classroom.controller;

import com.knu.cse.ApiResult.Response;
import com.knu.cse.classroom.dto.ClassRoomDTO;
import com.knu.cse.classroom.service.ClassRoomService;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.member.model.Member;
import com.knu.cse.reservation.domain.ReservationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.awt.desktop.ScreenSleepEvent;

@RestController
@RequiredArgsConstructor
@RequestMapping("classRoom")
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    //강의실 생성하기 및 좌석 생성
    @PostMapping("/add")
    public Response reservation(@Valid @RequestBody ClassRoomDTO classRoomDTO) throws Exception {
        try {
            classRoomService.RegistrationClassRoom(classRoomDTO.getBuilding(), classRoomDTO.getRoomNumber(), classRoomDTO.getTotalSeatNumber());
            return new Response("success", "강의실을 생성했습니다.",null);
        } catch (Exception e) {
            return new Response("error", "강의실 생성에 실패했습니다.", e.getMessage());
        }
    }

}
