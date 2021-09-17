package com.knu.cse.web.controller;

import com.knu.cse.reservationsave.domain.ReservationSave;
import com.knu.cse.reservationsave.repository.ReservationSaveRepository;
import com.knu.cse.reservationsave.service.ReservationSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/reservations")
@RequiredArgsConstructor
public class WebReservationController {

    private final ReservationSaveRepository reservationSaveRepository;
    private final ReservationSaveService reservationSaveService;

    @GetMapping
    public String reservations(Model model){
        List<ReservationSave> reservations = reservationSaveRepository.findAll(Sort.by("startTime").descending());
        model.addAttribute("reservations",reservations);

        return "reservations";
    }

    @GetMapping("/search")
    public String search(@RequestParam HashMap<String, String> paramMap, Model model){
        List<ReservationSave> reservations = reservationSaveService.search(paramMap);
        model.addAttribute("reservations", reservations);
        model.addAttribute("keywords", reservationSaveService.findKeywords(paramMap));
        model.addAttribute("searchTitle", paramMap);
        return "reservations";
    }

}