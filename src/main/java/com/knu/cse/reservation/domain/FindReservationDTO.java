package com.knu.cse.reservation.domain;

import com.knu.cse.classroom.domain.Building;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class FindReservationDTO {

    @NotNull
    Building building;
    @NotNull
    Long roomNumber;
    @NotNull
    Long seatNumber;

    public FindReservationDTO(Building building, Long roomNumber, Long seatNumber){
        this.building = building;
        this.roomNumber = roomNumber;
        this.seatNumber = seatNumber;
    }
}
