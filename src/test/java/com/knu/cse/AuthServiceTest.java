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
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
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
    SignUpForm signUpForm1 = new SignUpForm("ggolong2", "ggolong5@knu.ac.kr", "1234", "권현수", "2020202020", Gender.MALE, Major.ADVANCED);
    SignUpForm signUpForm2 = new SignUpForm("kcm0147", "ggolong2@knu.ac.kr", "1234", "김창묵", "2020202020", Gender.MALE, Major.ADVANCED);
    SignUpForm signUpForm3 = new SignUpForm("billbillbillbill", "ggolong3@knu.ac.kr", "1234", "이현섭", "2020202020", Gender.MALE, Major.ADVANCED);
    SignUpForm signUpForm4 = new SignUpForm("HyunMin", "ggolong4@knu.ac.kr", "1234", "한현민", "2020202020", Gender.MALE, Major.ADVANCED);

    @BeforeEach
    public void initDB() throws Exception{
        Member member1 = authService.signUpMember(signUpForm1);
        //Member member2 = authService.signUpMember(signUpForm2);
        //Member member3 = authService.signUpMember(signUpForm3);
        //Member member4 = authService.signUpMember(signUpForm4);
    }

    @DisplayName("회원가입 테스트")
    @Test
    public void 회원가입_유효한_이메일_테스트() throws Exception {
        this.member = authService.signUpMember(signUpForm4);
        Optional<Member> findMember = memberRepository.findByEmail(signUpForm4.getEmail());
        assertThat(this.member).isEqualTo(findMember.get());
    }

    @DisplayName("회원가입 테스트")
    @Test
    public void 회원가입_유효하지_않은_이메일_테스트() {
        signUpForm1.setEmail("ggolong@naver.com");
        try {
            this.member = authService.signUpMember(signUpForm1);
        } catch (Exception e){
            System.out.println("회원가입 실패 : " + e.getMessage());
            return;
        }
        // 다음 로직은 수행되지 않아야함.
        Optional<Member> findMember = memberRepository.findByEmail(signUpForm1.getEmail());
        assertThat(this.member).isEqualTo(findMember.get());
    }

    @DisplayName("로그인 테스트")
    @Test
    public void 로그인_테스트() throws Exception {
        this.member = authService.signUpMember(signUpForm3);
        System.out.println("Member : " + this.member.getEmail());
        SignInForm loginUser = new SignInForm(this.member.getEmail(), "12345678");
        System.out.println("로그인 폼 : " + loginUser.getEmail());
        try {
            authService.loginUser(loginUser);
            System.out.println("이메일!!!!!!!" + loginUser.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("이메일 전송 테스트")
    @Test
    public void 이메일_전송_테스트(){
        emailService.sendMail(signUpForm1.getEmail(),"테스트메일입니다.","이메일 본문 전송");
    }

    @Test
    public void JWT_키_생성_테스트(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[36]; // 36 bytes * 8 = 288 bits, a little bit more than
        // the 256 required bits
        random.nextBytes(bytes);
        var encoder = Base64.getUrlEncoder().withoutPadding();
        System.out.println(encoder.encodeToString(bytes));
    }

    @Test
    public void 로그인한_유저_찾기_테스트() throws Exception {
        //given
        this.member = authService.signUpMember(signUpForm2);
        SignInForm loginUser = new SignInForm(this.member.getEmail(), "1234");

        //when
        try {
            authService.loginUser(loginUser);
            System.out.println("이메일!!!!!!!" + loginUser.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //then
        assertThat(this.member.getNickname()).isEqualTo("kcm0147");
    }
}