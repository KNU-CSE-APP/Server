package com.knu.cse.member.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 비밀번호 찾기 시 인증된 회원의 비밀번호 변경을 위한 DTO
 *
 * [입력받는 파라미터]
 * 회원 이메일
 * 인증번호 인증 시 서버로부터 받는 허락 코드
 * 새로운 비밀번호
 */

@Getter
public class ValidatedPasswordForm {

    private String email;

    private String permissionCode;

    @NotNull
    @Length(min = 8, max = 20, message = "비밀번호의 길이는 8에서 20 사이여야 합니다!")
    private String password;

    public ValidatedPasswordForm(String email,
        @NotNull @Length(min = 8, max = 20, message = "비밀번호의 길이는 8에서 20 사이여야 합니다!") String password) {
        this.email = email;
        this.password = password;
    }
}
