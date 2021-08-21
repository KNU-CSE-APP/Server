package com.knu.cse.reservation.domain;

import com.knu.cse.classroom.domain.Building;
import com.knu.cse.classseat.domain.ClassSeat;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FindReservationDTO {


    private Building building;

    private Long roomNumber;

    private Long seatNumber;

    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private Long extensionNum;

    public FindReservationDTO(Reservation reservation){
        ClassSeat classSeat = reservation.getClassSeat();
        this.building = classSeat.getClassRoom().getBuilding();
        this.roomNumber = classSeat.getClassRoom().getNumber();
        this.seatNumber = classSeat.getNumber();
        this.startDate = reservation.getCreatedDate();
        this.dueDate = reservation.getDueDate();
        this.extensionNum = reservation.getExtensionNum();
    }
}
