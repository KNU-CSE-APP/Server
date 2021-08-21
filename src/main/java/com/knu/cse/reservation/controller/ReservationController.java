package com.knu.cse.reservation.controller;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classroom.service.ClassRoomService;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.repository.MemberRepository;
import com.knu.cse.reservation.domain.FindReservationDTO;
import com.knu.cse.reservation.domain.ReservationDTO;
import com.knu.cse.reservation.service.ReservationService;
import com.knu.cse.utils.ApiUtils;
import com.knu.cse.utils.ApiUtils.ApiResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static com.knu.cse.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthService authService;

    //반납하기
    @ApiOperation(value = "예약한 좌석 반납하기", notes="로그인 된 세션을 바탕으로 좌석 반납함.")
    @PostMapping("/delete")
    public ApiResult<String> deleteReservation() throws NotFoundException {
        Long userId = authService.getUserIdFromJWT();
        return ApiUtils.success(reservationService.unreserved(userId));
    }

    //예약하기
    @ApiOperation(value = "좌석 예약하기", notes="건물, 강의실 번호, 좌석 번호를 넘겨주면 로그인된 세션의 유저로 좌석을 예약함.")
    @PostMapping("/reservation")
    public ApiResult<String> reservation(@Valid @RequestBody ReservationDTO reservationDTO) throws Exception{
        Long userId = authService.getUserIdFromJWT();
        return success(reservationService.reservationSeat(userId, reservationDTO));
    }

    //현재 예약상태 찾기
    @ApiOperation(value = "현재 예약한 좌석 보기", notes="로그인된 세션의 유저의 예약 현황 보여줌")
    @PostMapping("/findReservation")
    public ApiResult<FindReservationDTO> findReservation() {
        Long userId = authService.getUserIdFromJWT();
        return success(reservationService.findReservation(userId));
    }

    @ApiOperation(value = "좌석 연장하기", notes="로그인된 세션의 유저의 좌석 연장함(최대3번)")
    @PostMapping("/extension")
    public ApiResult<Long> extension() throws Exception{
        Long userId = authService.getUserIdFromJWT();
        return success(reservationService.extensionSeat(userId));
    }

    //예약 전부 삭제
    @ApiOperation(value = "예약 전부 삭제하기", notes="현재 예약 전부 삭제")
    @PostMapping("/deleteAll")
    public ApiResult<String> deleteAll() throws Exception{
        reservationService.deleteAllReservation();
        return ApiUtils.success("모든 예약을 삭제했습니다.");
    }

    //그 강의실에 예약한 사람 수
    @ApiOperation(value = "해당 강의실을 예약한 사람 수", notes = "building(IT4 or IT5)와 강의실 번호를 넘기면 해당 강의실을 예약한 사람 수가 나옴.")
    @GetMapping("/numbers")
    public ApiResult<Integer> numberOfPeople(@RequestParam("building")Building building, @RequestParam("roomNumber") Long roomNumber) throws Exception{
        return ApiUtils.success(reservationService.numbersOfPeople(building,roomNumber));
    }

    // 현재 연장을 몇번했는지 출력
    @ApiOperation(value = "현재 연장 횟수 출력", notes = "로그인한 유저의 현재 연장 횟수 출력, 예약한 상태가 아니면 예외처리함")
    @GetMapping("/extensionNum")
    public ApiResult<Long> numberOfExtension() throws Exception{
        Long userId = authService.getUserIdFromJWT();
        return ApiUtils.success(reservationService.numberOfExtension(userId));

    }
}