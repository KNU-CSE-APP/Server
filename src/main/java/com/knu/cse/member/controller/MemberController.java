package com.knu.cse.member.controller;

import com.knu.cse.email.util.CookieUtil;
import com.knu.cse.email.util.JwtUtil;
import com.knu.cse.email.util.RedisUtil;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.dto.*;
import com.knu.cse.member.model.Member;
import com.knu.cse.email.service.AuthService;

import com.knu.cse.member.repository.MemberRepository;
import com.knu.cse.member.service.MemberService;
import com.knu.cse.utils.ApiUtils.ApiResult;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.knu.cse.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Slf4j
public class MemberController {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
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

    @ApiOperation(value = "회원가입", notes = "회원가입 API")
    @PostMapping("/signUp")
    public ApiResult<String> signUpSubmit(@Valid @RequestBody SignUpForm signUpForm) throws NotFoundException {
        Member savedMember = authService.signUpMember(signUpForm);
        return success("회원가입을 성공적으로 완료했습니다.");
    }

    @ApiOperation(value = "이메일 인증 번호 요청", notes = "회원가입 시 이메일 인증 번호를 요청하는 API")
    @GetMapping("/verify/{requestEmail}")
    public ApiResult<String> verify(@PathVariable("requestEmail") String requestEmail) throws NotFoundException {
        authService.sendVerificationMail(requestEmail);
        return success("성공적으로 인증메일을 보냈습니다.");
    }

    @ApiOperation(value = "이메일 인증", notes="회원가입 시 이메일로 전송한 인증 번호를 확인하는 API")
    @PostMapping("/verify/")
    public ApiResult<String> getVerify(@RequestBody VerifyEmailDto verifyEmailDto) throws IllegalStateException{
        String permissionCode = authService.verifyEmail(verifyEmailDto);
        return success(permissionCode);
    }

    @ApiOperation(value = "(E-mail, Nickname, userId, 프로필 이미지) 반환", notes="현재 로그인한 유저의 정보(닉네임, 이메일, 회원 번호, 프로필 이미지)를 반환하는 API")
    @GetMapping("/getUserEmailNickname")
    public ApiResult<MemberDto> getEmailNickname(HttpServletRequest req){
        Long userId = authService.getUserIdFromJWT(req);
        return success(new MemberDto(memberRepository.findById(userId).orElseThrow(()-> new NotFoundException ("존재하지 않는 회원입니다."))));
    }

    @ApiOperation(value = "프로필 이미지 변경", notes = "프로필 이미지를 파일 형태로 전송하여 저장할 수 있다.")
    @PutMapping("/profileImage")
    public ApiResult<String> uploadProfileImage(@RequestParam MultipartFile file, HttpServletRequest req)
        throws Exception {
        Long userId = authService.getUserIdFromJWT(req);
        return success(memberService.updateProfileImage(file, userId));
    }

    @ApiOperation(value = "비밀번호 변경", notes = "changePassword(String) : 변경하고자하는 비밀번호, currentPassword(String) : 현재 비밀번호 를 넘겨주면 validation 후 비밀번호 변경.")
    @PutMapping("/changePassword")
    public ApiResult<String> changePassword(@Valid @RequestBody ChangePasswordForm changePasswordForm, HttpServletRequest req){
        String encodedPassword = authService.getPasswordFromJWT(req);
        Long userId = authService.getUserIdFromJWT(req);
        authService.comparePassword(changePasswordForm.getCurrentPassword(),encodedPassword);
        memberService.changePassword(changePasswordForm.getChangePassword(), userId);
        return success("성공적으로 비밀번호를 변경했습니다.");
    }

}
