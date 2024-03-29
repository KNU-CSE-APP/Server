package com.knu.cse.member.dto;

import com.knu.cse.member.model.Gender;
import com.knu.cse.member.model.Major;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignUpForm {

    @NotNull
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,20}$")
    private String nickname;

    //@Email로 이메일 형식에 대한 모든 유효성을 체크할 수 없다! -> 어노테이션 내 구현된 isValid 메서드에서 null 을 허용함.
    //@NotNull 등과 함께 사용해야 완전하게 유효성을 검증할 수 있다.
    @Email
    @NotNull
    private String email;

    @NotNull
    @Length(min = 8, max = 20,message = "비밀번호의 길이는 8에서 20사이여야 합니다!")
    private String password;

    @NotNull
    private String username;

    @NotNull
    private String studentId;

    @NotNull
    private Gender gender;

    @NotNull
    private Major major;

    @NotNull
    private String permissionCode;

    public SignUpForm(
        @NotNull @Length(min = 3, max = 20) @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,20}$") String nickname,
        @Email @NotNull String email,
        @NotNull @Length(min = 8, max = 20) String password,
        @NotNull String username, @NotNull String studentId,
        @NotNull Gender gender,
        @NotNull Major major,
        @NotNull String permissionCode) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.username = username;
        this.studentId = studentId;
        this.gender = gender;
        this.major = major;
        this.permissionCode = permissionCode;
    }

    public SignUpForm() {}
}
