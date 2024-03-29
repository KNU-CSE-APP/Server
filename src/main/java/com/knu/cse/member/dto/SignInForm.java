package com.knu.cse.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInForm {

    @Email
    @NotBlank
    private String email;

    private String password;

    public SignInForm(
        @Email @NotBlank String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignInForm(){}
}
