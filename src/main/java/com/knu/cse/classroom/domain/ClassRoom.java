package com.knu.cse.classroom.domain;

import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.reservation.domain.Reservation;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CLASS_ROOM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ClassRoom {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private Long number;

    @Enumerated(EnumType.STRING)
    private Building building;

    private Long totalSeats;

//    @OneToMany(mappedBy = "classRoom",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "classRoom")
    private List<ClassSeat> classSeats;

}