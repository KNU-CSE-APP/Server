package com.knu.cse.reservationsave.repository;

import com.knu.cse.reservationsave.domain.ReservationSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSaveRepository extends JpaRepository<ReservationSave, Long> {
}
