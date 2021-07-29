package com.knu.cse.email.service;

import com.knu.cse.email.util.RedisUtil;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.dto.VerifyEmailDto;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.model.MemberRole;
import com.knu.cse.member.dto.SignInForm;
import com.knu.cse.member.dto.SignUpForm;
import com.knu.cse.member.repository.MemberRepository;
import java.util.Base64;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    public Member signUpMember(SignUpForm signUpForm) throws IllegalStateException, IllegalArgumentException {

        if(!isKnuStudent(signUpForm.getEmail())){
            throw new IllegalArgumentException("경북대학교 이메일(knu.ac.kr)만 가입이 가능합니다.");
        }
        if(isDuplicateMember(signUpForm.getEmail())){
            throw new IllegalStateException("이미 가입된 이메일 회원입니다.");
        }
        if(isDuplicateNickName(signUpForm.getNickname())){
            throw new IllegalStateException("닉네임 중복되었습니다. 다른 닉네임으로 해주세요.");
        }

        String permittedEmail = redisUtil.getData(signUpForm.getPermissionCode());
        if(permittedEmail == null || !permittedEmail.equals(signUpForm.getEmail())){
            throw new IllegalArgumentException("이메일 인증이 올바르게 수행되지 않았습니다.");
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

    private boolean isDuplicateNickName(String nickName) { return memberRepository.existsByNickname(nickName); }

    private boolean isDuplicateMember(String email) {
        return memberRepository.existsByEmail(email);
    }

    private boolean isKnuStudent(String email) {
        String emailAddress = email.substring(email.indexOf("@") + 1);
        return emailAddress.equals("knu.ac.kr");
    }

    public Member loginUser(SignInForm signInForm) throws NotFoundException{
        if(!isKnuStudent(signInForm.getEmail())) throw new IllegalArgumentException("경북대학교 학생이 아닙니다.");
        Member member = memberRepository.findByEmail(signInForm.getEmail()).orElseThrow(()->
            new NotFoundException("유저가 존재하지 않습니다."));
        log.info("플레인 비밀번호" + signInForm.getPassword());
        log.info("인코딩된 비밀번호" + member.getPassword());

        boolean matcheResult = passwordEncoder.matches(signInForm.getPassword(), member.getPassword());
        if(!matcheResult)
            throw new NotFoundException("비밀번호가 틀립니다.");
        return member;
    }

    public void sendVerificationMail(String email) throws NotFoundException {
        int authentication = (int)(Math.random()*1000000);
        String content = "다음의 인증 번호를 입력해 인증을 완료해주세요. \n" + authentication ;
        redisUtil.setDataExpire(authentication+"",email, 60 * 3L);
        emailService.sendMail(email, "[경북대학교 컴퓨터학부 APP] 회원가입 인증메일입니다.", content);
        log.info(content);
    }

    public String verifyEmail(VerifyEmailDto verifyEmailDto) throws IllegalStateException {
        String email = redisUtil.getData(verifyEmailDto.getCode());
        if(email == null) throw new IllegalStateException("인증번호가 올바르지 않습니다.");
        if(email.equals(verifyEmailDto.getEmail())) {
            int permissionCode = (int)(Math.random()*1000000);
            redisUtil.setDataExpire(permissionCode+"",verifyEmailDto.getEmail(), 60 * 3L);
            return permissionCode+"";
        }
        else {
            redisUtil.deleteData(verifyEmailDto.getCode());
            throw new IllegalStateException("인증번호가 올바르지 않습니다.");
        }
    }

    public Member findByEmail(String email) throws NotFoundException {
        return memberRepository.findByEmail(email).orElseThrow(()->
            new NotFoundException("존재하지 않는 이메일입니다."));
    }

    public Long getUserIdFromJWT(HttpServletRequest req) throws NotFoundException {
        String claim = "";
        for (Cookie cookie : req.getCookies()) {
            if(cookie.getName().equals("accessToken")){
                claim = cookie.getValue();
                break;
            }
        }
        int start = claim.indexOf(".") + 1;
        int end = claim.lastIndexOf(".");
        if(start == -1 || end == -1){
            throw new NotFoundException("아직 JWT 토큰을 발급받지 않은 상태입니다." + claim);
        }
        String encodedPayload = claim.substring(start, end);
        String email = new String(Base64.getDecoder().decode(encodedPayload)).split("\"")[3];
        return findByEmail(email).getId();
    }
}
