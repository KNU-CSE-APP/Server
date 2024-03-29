package com.knu.cse.classroom.domain;

import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.reservation.domain.Reservation;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id","number","building","totalSeats"})
public class ClassRoom {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private Long number;

    @Enumerated(EnumType.STRING)
    private Building building;

    private Long totalSeats;

    @OneToMany(mappedBy = "classRoom")
    private List<ClassSeat> classSeats = new ArrayList<>();

    public void DownTotalSeats(){
        this.totalSeats -= 1;
    }
    public void UpTotalSeats(){
        this.totalSeats += 1;
    }

    @Builder
    public ClassRoom(Long number,Building building, Long totalSeats){
        this.number = number;
        this.building = building;
        this.totalSeats = totalSeats;
    }
}