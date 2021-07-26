package com.knu.cse.classroom.dto;

import com.knu.cse.classroom.domain.Building;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ClassRoomDTO {
    @NotNull
    private Building building;
    @NotNull
    private Long roomNumber;
    @NotNull
    private Long totalSeatNumber;

    public ClassRoomDTO(Building building, Long roomNumber,Long totalSeatNumber){
        this.building = building;
        this.roomNumber = roomNumber;
        this.totalSeatNumber = totalSeatNumber;
    }

}
