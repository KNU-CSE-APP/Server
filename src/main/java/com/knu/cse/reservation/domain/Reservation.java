package com.knu.cse.reservation.domain;

import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.member.model.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name="reservation_id")
    private Long id;

    private LocalDateTime dueDate;
    private Long extensionNum;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private ClassSeat classSeat;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member){
        if (this.member != null){
            this.member.getReservations().remove(this);
        }
        this.member = member;
        member.getReservations().add(this);
    }

    public Reservation(Long extensionNum, ClassSeat classSeat, Member member){
        this.dueDate = LocalDateTime.now().plusHours(6);
        this.extensionNum = extensionNum;
        this.classSeat = classSeat;
        setMember(member);
    }

}