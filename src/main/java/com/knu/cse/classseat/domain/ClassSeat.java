package com.knu.cse.classseat.domain;

import com.knu.cse.base.BaseEntity;
import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.reservation.domain.Reservation;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id","number","status"})
public class ClassSeat extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "seat_id")
    private Long id;

    private Long number;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private ClassRoom classRoom;

    @OneToOne(mappedBy = "classSeat")
    private Reservation reservation;

    public void changeReserved(){
        this.status = Status.RESERVED;
        this.classRoom.DownTotalSeats();
    }
    public void changeUnReserved(){
        this.status = Status.UNRESERVED;
        this.classRoom.UpTotalSeats();
    }

    public void setReservation(Reservation reservation){
        reservation.setClassSeat(this);
        this.reservation = reservation;
    }


    public void setClassRoom(ClassRoom classRoom){
        if (this.classRoom != null){
            this.classRoom.getClassSeats().remove(this);
        }
        this.classRoom = classRoom;
        classRoom.getClassSeats().add(this);
    }

    @Builder
    public ClassSeat(Long number, Status status, ClassRoom classRoom){
        this.number = number;
        this.status = status;
        setClassRoom(classRoom);
    }
}

