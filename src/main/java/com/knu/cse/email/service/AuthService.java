package com.knu.cse.email.service;

import com.knu.cse.email.util.RedisUtil;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.model.MemberRole;
import com.knu.cse.member.dto.SignInForm;
import com.knu.cse.member.dto.SignUpForm;
import com.knu.cse.member.repository.MemberRepository;
import java.util.Base64;
import java.util.UUID;
import javassist.NotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RedisUtil redisUtil;

    @Transactional
    public Member signUpMember(SignUpForm signUpForm) throws Exception {

        if(!isKnuStudent(signUpForm.getEmail())){
            throw new Exception("경북대학교 이메일(knu.ac.kr)만 가입이 가능합니다.");
        }
        if(isDuplicateMember(signUpForm.getEmail())){
            throw new Exception("이미 가입된 회원입니다.");
        }

        Member member = Member.builder()
            .email(signUpForm.getEmail())
            .password(passwordEncoder.encode(signUpForm.getPassword()))
            .username(signUpForm.getUsername())
            .nickname(signUpForm.getNickname())
            .studentId(signUpForm.getStudentId())
            .gender(signUpForm.getGender())
            .major(signUpForm.getMajor())
            .role(MemberRole.ROLE_NOT_PERMITTED)
            .build();

        return memberRepository.save(member);
    }

    private boolean isDuplicateMember(String email) {
        return memberRepository.existsByEmail(email);
    }

    private boolean isKnuStudent(String email) {
        String emailAddress = email.substring(email.indexOf("@") + 1);
        return emailAddress.equals("knu.ac.kr");
    }

    public Member loginUser(SignInForm signInForm) throws Exception{
        Member member = memberRepository.findByEmail(signInForm.getEmail());
        if(member==null) throw new Exception ("멤버가 조회되지 않음");
        log.info("플레인 비밀번호" + signInForm.getPassword());
        log.info("인코딩된 비밀번호" + member.getPassword());;

        boolean matcheResult = passwordEncoder.matches(signInForm.getPassword(), member.getPassword());
        if(!matcheResult)
            throw new Exception ("비밀번호가 틀립니다.");
        return member;
    }

    public void sendVerificationMail(Member member) throws NotFoundException {
        String VERIFICATION_LINK = "http://localhost:8080/user/verify/";
        if(member==null) throw new NotFoundException("멤버가 조회되지 않음");
        UUID uuid = UUID.randomUUID();
        String content = "다음의 링크를 통해 인증을 완료해주세요. \n" + VERIFICATION_LINK+uuid.toString() ;
        redisUtil.setDataExpire(uuid.toString(),member.getEmail(), 60 * 30L);
        emailService.sendMail(member.getEmail(), "[경북대학교 컴퓨터학부 APP] 회원가입 인증메일입니다.", content);
        log.info(content);
    }

    public void verifyEmail(String key) throws NotFoundException {
        String email = redisUtil.getData(key);
        Member member = memberRepository.findByEmail(email);
        if(member==null) throw new NotFoundException("멤버가 조회되지않음");
        modifyUserRole(member, MemberRole.ROLE_USER);
        redisUtil.deleteData(key);
    }

    public void modifyUserRole(Member member, MemberRole userRole){
        member.changeRole(userRole);
        memberRepository.save(member);
    }


    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public String getEmailFromJWT(HttpServletRequest req, HttpServletResponse res){
        String claim = req.getCookies()[0].getValue();
        int start = claim.indexOf(".") + 1;
        int end = claim.lastIndexOf(".");
        String encodedPayload = claim.substring(start, end);
        return new String(Base64.getDecoder().decode(encodedPayload)).split("\"")[3];
    }
}
