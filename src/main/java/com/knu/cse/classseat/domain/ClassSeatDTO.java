package com.knu.cse.classseat.domain;

import com.knu.cse.classroom.domain.Building;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ClassSeatDTO {

    private Long number;

    @Enumerated(EnumType.STRING)
    private Status status;

    public ClassSeatDTO(Long number, Status status){
        this.number = number;
        this.status = status;
    }
}
