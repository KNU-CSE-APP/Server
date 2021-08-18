package com.knu.cse.reservation.controller;

import com.knu.cse.classroom.domain.ClassRoom;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import javax.validation.Valid;
import static com.knu.cse.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthService authService;
    private final ClassRoomService classRoomService;
    private final MemberRepository memberRepository;


    //반납하기
    @ApiOperation(value = "예약한 좌석 반납하기", notes="로그인 된 세션을 바탕으로 좌석 반납함.")
    @PostMapping("/delete")
    public ApiResult<String> deleteReservation(HttpServletRequest request) throws NotFoundException {
        Long userId = authService.getUserIdFromJWT();
        Optional<Member> member = Optional.ofNullable(memberRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("등록된 회원이 아닙니다.")
        ));

        reservationService.unreserved(member.get().getEmail());
        return ApiUtils.success("좌석을 반납했습니다.");
    }

    //예약하기
    @ApiOperation(value = "좌석 예약하기", notes="건물, 강의실 번호, 좌석 번호를 넘겨주면 로그인된 세션의 유저로 좌석을 예약함.")
    @PostMapping("/reservation")
    public ApiResult<String> reservation(@Valid @RequestBody ReservationDTO reservationDTO, HttpServletRequest request) throws Exception{
        Long userId = authService.getUserIdFromJWT();
        Optional<Member> member = Optional.ofNullable(memberRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("등록된 회원이 아닙니다")
        ));

        ClassSeat classSeat = classRoomService.findClassSeatByBuildingAndRoomAndSeatNum(reservationDTO.getBuilding(), reservationDTO.getRoomNumber(), reservationDTO.getSeatNumber());

        reservationService.reservationSeat(member.get().getId(),classSeat.getId());
        return ApiUtils.success("자리 예약에 성공했습니다.");
    }

    //현재 예약상태 찾기
    @ApiOperation(value = "현재 예약한 좌석 보기", notes="로그인된 세션의 유저의 예약 현황 보여줌")
    @PostMapping("/findReservation")
    public ApiResult<FindReservationDTO> findReservation(HttpServletRequest request) {
        Long userId = authService.getUserIdFromJWT();
        FindReservationDTO findReservationDTO = reservationService.findReservation(userId);
        return success(findReservationDTO);
    }

    @ApiOperation(value = "좌석 연장하기", notes="로그인된 세션의 유저의 좌석 연장함(최대3번)")
    @PostMapping("/extension")
    public ApiResult<Long> extension(HttpServletRequest request) throws Exception{
        Long userId = authService.getUserIdFromJWT();
        Optional<Member> member = Optional.ofNullable(memberRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("등록된 회원이 아닙니다.")
        ));

        Long extensionNumber = reservationService.extensionSeat(member.get().getId());
        return ApiUtils.success(extensionNumber);
    }

    //예약하기
    @ApiOperation(value = "예약 전부 삭제하기", notes="현재 예약 전부 삭제")
    @PostMapping("/deleteAll")
    public ApiResult<String> deleteAll() throws Exception{



        reservationService.deleteAllReservation();
        return ApiUtils.success("모든 예약을 삭제했습니다.");
    }
}