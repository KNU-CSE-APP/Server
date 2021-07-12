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
@Builder
public class SignUpForm {

    @NotNull
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickname;

    //@Email로 이메일 형식에 대한 모든 유효성을 체크할 수 없다! -> 어노테이션 내 구현된 isValid 메서드에서 null 을 허용함.
    //@NotNull 등과 함께 사용해야 완전하게 유효성을 검증할 수 있다.
    @Email
    @NotNull
    private String email;

    @NotNull
    @Length(min = 8, max = 20)
    private String password;

    @NotNull
    private String username;

    @NotNull
    private String studentId;

    @NotNull
    private Gender gender;

    @NotNull
    private Major major;
}
