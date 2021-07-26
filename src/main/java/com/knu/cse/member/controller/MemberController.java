package com.knu.cse.member.controller;

import com.knu.cse.email.util.CookieUtil;
import com.knu.cse.email.util.JwtUtil;
import com.knu.cse.email.util.RedisUtil;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.dto.LoginSuccessDto;
import com.knu.cse.member.dto.MemberDto;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.dto.RequestVerifyEmail;
import com.knu.cse.member.dto.SignInForm;
import com.knu.cse.member.dto.SignUpForm;
import com.knu.cse.email.service.AuthService;

import com.knu.cse.member.repository.MemberRepository;
import com.knu.cse.utils.ApiUtils;
import com.knu.cse.utils.ApiUtils.ApiResult;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.knu.cse.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Slf4j
public class MemberController {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    @ApiOperation(value = "로그인", notes = "회원정보를 입력해 로그인을 수행하고 JWT 토큰 발급")
    @PostMapping("/signIn")
    public ApiResult<LoginSuccessDto> signIn(@Valid @RequestBody SignInForm signInForm, HttpServletResponse res) throws Exception {
        final Member member = authService.loginUser(signInForm);
        final String token = jwtUtil.generateToken(member.getEmail());
        final String refreshJwt = jwtUtil.generateRefreshToken(member);
        Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
        Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
        redisUtil.setDataExpire(refreshJwt, member.getEmail(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
        res.addCookie(accessToken);
        res.addCookie(refreshToken);
        LoginSuccessDto lsd = new LoginSuccessDto(member.getId(), member.getNickname());
        log.info("발급받은 토큰 : " + token);
        return success(lsd);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입 메소드")
    @PostMapping("/signUp")
    public ApiResult<String> signUpSubmit(@Valid @RequestBody SignUpForm signUpForm, Errors errors) throws NotFoundException {
        Member savedMember = authService.signUpMember(signUpForm);
        authService.sendVerificationMail(savedMember);
        return success("회원가입을 성공적으로 완료했습니다.");
    }

    @ApiOperation(value = "이메일 인증", notes = "회원가입 시 이메일 인증을 수행하는 메소드")
    @PostMapping("/verify")
    public ApiResult<String> verify(@RequestBody RequestVerifyEmail requestVerifyEmail) throws NotFoundException {
        Member member = authService.findByEmail(requestVerifyEmail.getEmail());
        authService.sendVerificationMail(member);
        return success("성공적으로 인증메일을 보냈습니다.");
    }

    @ApiOperation(value = "이메일 인증", notes="회원가입 시 이메일로 전송한 인증 링크를 확인하는 메소드")
    @GetMapping("/verify/{key}")
    public ApiResult<String> getVerify(@PathVariable String key) throws NotFoundException{
        authService.verifyEmail(key);
        return success("성공적으로 인증메일을 확인했습니다.");
    }

    @ApiOperation(value = "회원 E-mail과 Nickname, userId 반환", notes="현재 로그인한 유저의 정보(닉네임, 이메일, 회원 번호)을 반환하는 메소드")
    @GetMapping("/getUserEmailNickname")
    public ApiResult<MemberDto> getEmailNickname(HttpServletRequest req){
        Long userId = authService.getUserIdFromJWT(req);
        return success(new MemberDto(memberRepository.findById(userId).orElseThrow(()-> new NotFoundException ("존재하지 않는 회원입니다."))));
    }
}
