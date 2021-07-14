package com.knu.cse.classseat.domain;

import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.reservation.domain.Reservation;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CLASS_SEAT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id","number","status"})

public class ClassSeat {

    @Id @GeneratedValue
    @Column(name = "seat_id")
    private Long id;

    private Integer number;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private ClassRoom classRoom;

    @OneToOne(mappedBy = "classSeat")
    private Reservation reservation;


    public void setClassRoom(ClassRoom classRoom){
        if (this.classRoom != null){
            this.classRoom.getClassSeats().remove(this);
        }
        this.classRoom = classRoom;
        classRoom.getClassSeats().add(this);
    }

    public ClassSeat(Integer number, Status status, ClassRoom classRoom){
        this.number = number;
        this.status = status;
        setClassRoom(classRoom);

    }



}

