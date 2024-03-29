package com.knu.cse.reservation.domain;

import com.knu.cse.classroom.domain.Building;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ReservationDTO {

    @NotNull
    Building building;
    @NotNull
    Long roomNumber;
    @NotNull
    Long seatNumber;

    public ReservationDTO(Building building, Long roomNumber, Long seatNumber){
        this.building = building;
        this.roomNumber = roomNumber;
        this.seatNumber = seatNumber;
    }

}
