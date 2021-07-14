package com.knu.cse;

import static org.assertj.core.api.Assertions.assertThat;

import com.knu.cse.member.model.Member;
import com.knu.cse.member.model.Gender;
import com.knu.cse.member.model.Major;
import com.knu.cse.member.dto.SignInForm;
import com.knu.cse.member.dto.SignUpForm;
import com.knu.cse.member.repository.MemberRepository;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MemberRepository memberRepository;

    Member member;
    SignUpForm signUpForm;

    @BeforeEach()
    public void initMember(){
        this.signUpForm = SignUpForm.builder()
                .email("ggolong@knu.ac.kr")
                .password("12345678")
                .username("권현수")
                .nickname("ggolong")
                .gender(Gender.MALE)
                .major(Major.ADVANCED)
                .studentId("2016115613")
                .build();
    }

    @DisplayName("회원가입 테스트")
    @Test
    public void 회원가입_테스트() throws Exception {
        this.member = authService.signUpMember(signUpForm);
        Member findMember = memberRepository.findByEmail(signUpForm.getEmail());
        assertThat(this.member).isEqualTo(findMember);
    }

    @DisplayName("로그인 테스트")
    @Test
    public void 로그인_테스트() throws Exception {
        this.member = authService.signUpMember(signUpForm);
        System.out.println("Member : " + this.member.getEmail());
        SignInForm loginUser = new SignInForm(this.member.getEmail(), "12345678");
        System.out.println("로그인 폼 : " + loginUser.getEmail());
        try {
            authService.loginUser(loginUser);
            System.out.println("이메일!!!!!!!" + loginUser.getEmail());
            //log.info("로그인 성공");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("이메일 전송 테스트")
    @Test
    public void 이메일_전송_테스트(){
        emailService.sendMail(this.member.getEmail(),"테스트메일입니다.","잘 되는건가");
    }

}