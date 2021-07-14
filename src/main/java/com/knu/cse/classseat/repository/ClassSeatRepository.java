package com.knu.cse.classseat.repository;

import com.knu.cse.classseat.domain.ClassSeat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClassSeatRepository extends JpaRepository<ClassSeat,Long> {
}