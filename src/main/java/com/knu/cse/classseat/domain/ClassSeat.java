package com.knu.cse.classseat.domain;

import com.knu.cse.classroom.domain.ClassRoom;
import com.knu.cse.reservation.domain.Reservation;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CLASS_SEAT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class ClassSeat {

    @Id @GeneratedValue
    @Column(name = "seat_id")
    private Long id;

    private Integer number;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name="room_id")
    private ClassRoom classRoom;

    @OneToOne(mappedBy = "classSeat")
    private Reservation reservation;


}
