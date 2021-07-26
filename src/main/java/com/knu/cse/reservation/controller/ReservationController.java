package com.knu.cse.reservation.controller;

import com.knu.cse.ApiResult.Response;
import com.knu.cse.classroom.service.ClassRoomService;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.classseat.service.ClassSeatService;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.email.util.CookieUtil;
import com.knu.cse.email.util.JwtUtil;
import com.knu.cse.email.util.RedisUtil;
import com.knu.cse.member.dto.RequestVerifyEmail;
import com.knu.cse.member.dto.SignInForm;
import com.knu.cse.member.model.Member;
import com.knu.cse.reservation.domain.FindReservationDTO;
import com.knu.cse.reservation.domain.Reservation;
import com.knu.cse.reservation.domain.ReservationDTO;
import com.knu.cse.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthService authService;
    private final ClassRoomService classRoomService;
    private final ClassSeatService classSeatService;


    //반납하기
    @PostMapping("/delete")
    public Response deleteReservation(@Valid @RequestBody RequestVerifyEmail email){
        try{
            reservationService.unreserved(email.getEmail());
            return new Response("success", "좌석을 반납했습니다.", null);
        }catch (Exception e){
            return new Response("error", "반납할 좌석이 없습니다.", e.getMessage());
        }
    }

    //예약하기
    @PostMapping("/reservation")
    public Response reservation(@Valid @RequestBody ReservationDTO reservationDTO) throws Exception {
        try {
            Member member = authService.findByEmail(reservationDTO.getEmail());
            ClassSeat classSeat = classRoomService.findClassSeatByBuildingAndRoomAndSeatNum(reservationDTO.getBuilding(), reservationDTO.getRoomNumber(), reservationDTO.getSeatNumber());
            reservationService.reservationSeat(member.getId(),classSeat.getId());
            return new Response("success", "자리 예약에 성공했습니다.",null);
        } catch (Exception e) {
            return new Response("error", "자리 예약에 실패했습니다", e.getMessage());
        }
    }

    //현재 예약상태 찾기
    @GetMapping("/findReservation")
    public Response findReservation(@Valid @RequestBody RequestVerifyEmail email){
        try{
            Member member = authService.findByEmail(email.getEmail());

            FindReservationDTO findReservationDTO = new FindReservationDTO(
                    member.getReservations().get(0).getClassSeat().getClassRoom().getBuilding(),
                    member.getReservations().get(0).getClassSeat().getClassRoom().getNumber(),
                    member.getReservations().get(0).getClassSeat().getNumber()
            );

            return new Response("success", "예약한 목록들입니다.", findReservationDTO);
        } catch (Exception e){
            return new Response("error", "현재 예약된 좌석이 없습니다.", e.getMessage());
        }
    }

    @PostMapping("/extension")
    public Response extension(@Valid @RequestBody RequestVerifyEmail email){
        try{
            Member member = authService.findByEmail(email.getEmail());
            Long extensionNumber = reservationService.extensionSeat(member.getId());
            if (extensionNumber != -1L) {
                return new Response("success", "좌석 연장에 성공했습니다", extensionNumber);
            }
            else{
                return new Response("error", "연장 횟수를 초과했습니다.", null);
            }
        }catch (Exception e){
            return new Response("error", "현재 예약된 좌석이 없습니다.", e.getMessage());
        }
    }
}