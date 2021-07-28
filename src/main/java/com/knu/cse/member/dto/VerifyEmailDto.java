package com.knu.cse.member.dto;

import lombok.Data;

@Data
public class VerifyEmailDto {

    private String email;
    private String code;
}
