package com.knu.cse.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ChangePasswordForm {
    private String currentPassword;

    @NotNull
    @Length(min = 8, max = 20, message = "비밀번호의 길이는 8에서 20 사이여야 합니다!")
    private String changePassword;

    public ChangePasswordForm(String currentPassword,
                              @NotNull @Length(min = 8, max = 20) String changePassword){
        this.currentPassword = currentPassword;
        this.changePassword = changePassword;
    }
}
