package com.knu.cse.reservation.domain;

import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.member.model.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
public class Reservation extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name="reservation_id")
    private Long id;

    private LocalDateTime dueDate;
    private Long extensionNum;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private ClassSeat classSeat;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;


    public void setMember(Member member){
    this.member = member;
}

    public void setClassSeat(ClassSeat classSeat){
        this.classSeat = classSeat;
    }

    public Reservation(){
        this.dueDate = LocalDateTime.now().plusHours(6);
        this.extensionNum = 0L;
    }

    public void updateTime(){
        this.dueDate = LocalDateTime.now().plusHours(6);
    }

    public void upExtensionNum(){
        this.extensionNum += 1;
    }
    public void downExtensionNum(){
        this.extensionNum -= 1;
    }

    public static Reservation createReservation(Member member, ClassSeat classSeat){
        Reservation reservation = new Reservation();

        classSeat.changeReserved();
        reservation.setMember(member);
        reservation.setClassSeat(classSeat);
        member.setReservation(reservation);
        classSeat.setReservation(reservation);

        return reservation;
    }

}