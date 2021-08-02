package com.knu.cse.classroom.controller;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.classroom.dto.ClassRoomDTO;
import com.knu.cse.classroom.service.ClassRoomService;
import com.knu.cse.classseat.domain.ClassSeatDTO;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.utils.ApiUtils;
import com.knu.cse.utils.ApiUtils.ApiResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.knu.cse.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("classRoom")
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    //강의실 생성하기 및 좌석 생성
    @ApiOperation(value = "강의실 생성 및 좌석 생성", notes="건물, 강의실 번호, 총 좌석수를 넘겨주면 생성함.")
    @PostMapping("/add")
    public ApiResult<String> reservation(@Valid @RequestBody ClassRoomDTO classRoomDTO) throws NotFoundException {

        classRoomService.RegistrationClassRoom(classRoomDTO.getBuilding(), classRoomDTO.getRoomNumber(), classRoomDTO.getTotalSeatNumber());
        return success("강의실을 생성했습니다.");
    }

    //강의실 모든 좌석 조회하기
    @ApiOperation(value = "해당 강의실 안 모든 좌석 상태 조회", notes="건물과 강의실 번호를 GET 방식으로 넘겨주면 해당 강의실 안 모든 좌석 상태를 조회함.")
    @GetMapping("/searchSeats/{building}/{roomNumber}")
    public ApiResult<List<ClassSeatDTO>> searchAllSeatsInRoom(@PathVariable Building building, @PathVariable Long roomNumber){
        return success(classRoomService.LookupClassRoom(roomNumber, building));
    }


//    @GetMapping("/verify/{key}")
//    public ApiResult<String> getVerify(@PathVariable String key) throws NotFoundException{

}
