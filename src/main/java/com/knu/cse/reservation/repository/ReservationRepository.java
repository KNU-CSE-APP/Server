package com.knu.cse.reservation.repository;

import com.knu.cse.member.repository.MemberRepository;
import com.knu.cse.reservation.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Boolean existsByMemberId(Long memberId);

    Reservation findByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}

