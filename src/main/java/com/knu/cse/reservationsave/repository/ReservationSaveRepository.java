package com.knu.cse.reservationsave.repository;

import com.knu.cse.reservationsave.domain.ReservationSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationSaveRepository extends JpaRepository<ReservationSave, Long>, QuerydslPredicateExecutor<ReservationSave> {

}
