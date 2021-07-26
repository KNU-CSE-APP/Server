package com.knu.cse.classroom.controller;

import com.knu.cse.classroom.dto.ClassRoomDTO;
import com.knu.cse.classroom.service.ClassRoomService;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.utils.ApiUtils;
import com.knu.cse.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import static com.knu.cse.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("classRoom")
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    //강의실 생성하기 및 좌석 생성
    @PostMapping("/add")
    public ApiResult<String> reservation(@Valid @RequestBody ClassRoomDTO classRoomDTO) throws NotFoundException {

        classRoomService.RegistrationClassRoom(classRoomDTO.getBuilding(), classRoomDTO.getRoomNumber(), classRoomDTO.getTotalSeatNumber());
        return success("강의실을 생성했습니다.");
    }

}
