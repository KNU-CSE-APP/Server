package com.knu.cse.reservation.controller;

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
}