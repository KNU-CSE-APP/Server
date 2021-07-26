package com.knu.cse.reservation.controller;

import com.knu.cse.classroom.service.ClassRoomService;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.dto.RequestVerifyEmail;
import com.knu.cse.member.model.Member;
import com.knu.cse.reservation.domain.FindReservationDTO;
import com.knu.cse.reservation.domain.ReservationDTO;
import com.knu.cse.reservation.service.ReservationService;
import com.knu.cse.utils.ApiUtils;
import com.knu.cse.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthService authService;
    private final ClassRoomService classRoomService;


    //반납하기
    @PostMapping("/delete")
    public ApiResult<String> deleteReservation(@Valid @RequestBody RequestVerifyEmail email) throws NotFoundException {
        reservationService.unreserved(email.getEmail());
        return ApiUtils.success("좌석을 반납했습니다.");
    }

    //예약하기
    @PostMapping("/reservation")
    public ApiResult<String> reservation(@Valid @RequestBody ReservationDTO reservationDTO) throws Exception{
        Member member = authService.findByEmail(reservationDTO.getEmail());
        ClassSeat classSeat = classRoomService.findClassSeatByBuildingAndRoomAndSeatNum(reservationDTO.getBuilding(), reservationDTO.getRoomNumber(), reservationDTO.getSeatNumber());
        reservationService.reservationSeat(member.getId(),classSeat.getId());
        return ApiUtils.success("자리 예약에 성공했습니다.");
    }

    //현재 예약상태 찾기
    @GetMapping("/findReservation")
    public ApiResult<FindReservationDTO> findReservation(@Valid @RequestBody RequestVerifyEmail email) {

        Member member = authService.findByEmail(email.getEmail());

        FindReservationDTO findReservationDTO = new FindReservationDTO(
            member.getReservations().get(0).getClassSeat().getClassRoom().getBuilding(),
            member.getReservations().get(0).getClassSeat().getClassRoom().getNumber(),
            member.getReservations().get(0).getClassSeat().getNumber()
        );

        return ApiUtils.success(findReservationDTO);
    }

    @PostMapping("/extension")
    public ApiResult<Long> extension(@Valid @RequestBody RequestVerifyEmail email) throws Exception{
        Member member = authService.findByEmail(email.getEmail());
        Long extensionNumber = reservationService.extensionSeat(member.getId());
        return ApiUtils.success(extensionNumber);
    }
}