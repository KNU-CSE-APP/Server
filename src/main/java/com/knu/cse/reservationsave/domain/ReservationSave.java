package com.knu.cse.reservationsave.domain;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.member.model.Major;
import com.knu.cse.member.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationSave {

    @Id @GeneratedValue
    @Column(name = "reservatoin_save_id")
    private Long id;

    private Building building;
    private Long roomNumber;
    private Long seatNumber;

    private String email;
    private String username;
    private String studentId;
    private Major major;

    private LocalDateTime startTime;

    private Boolean returnCheck;


    @Builder
    public ReservationSave(Building building, Long roomNumber, Long seatNumber,
                           Member member,
                           LocalDateTime startTime,Boolean returnCheck){
        this.building = building;
        this.roomNumber = roomNumber;
        this.seatNumber = seatNumber;

        this.email= member.getEmail();
        this.username = member.getUsername();
        this.studentId = member.getStudentId();
        this.major = member.getMajor();

        this.startTime = startTime;
        this.returnCheck = returnCheck;
    }

}