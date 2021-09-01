package com.knu.cse.reservationsave.service;

import com.knu.cse.reservationsave.domain.ReservationSave;
import com.knu.cse.reservationsave.domain.ReservationSavePredicate;
import com.knu.cse.reservationsave.repository.ReservationSaveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationSaveService {

    private final ReservationSaveRepository reservationSaveRepository;

    @Transactional(readOnly = true)
    public List<ReservationSave> search(HashMap<String, String> paramMap){
        return (List<ReservationSave>) reservationSaveRepository.findAll(ReservationSavePredicate.search(paramMap));
    }

    @Transactional(readOnly = true)
    public HashMap<String, String> findKeywords(HashMap<String, String> paramMap){
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> title = makeTitle();
        for (String s : paramMap.keySet()) {
            if(!paramMap.get(s).equals("")){
                map.put(title.get(s), paramMap.get(s));
            }
        }
        return map;
    }

    private HashMap<String,String> makeTitle() {
        HashMap<String, String> title = new HashMap<String, String>();
        title.put("searchBuilding", "건물");
        title.put("searchRoom", "강의실");
        title.put("searchSeat", "좌석 번호");
        title.put("searchEmail", "이메일");
        title.put("searchName", "이름");
        title.put("searchStudentId", "학번");
        title.put("currentDate", "날짜");

        return title;
    }
}
