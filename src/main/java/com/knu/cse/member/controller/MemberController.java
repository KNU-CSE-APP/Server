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
import org.springframework.web.bind.annotation.*;
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

    @ApiOperation(value = "회원가입 인증번호 발급", notes = "회원가입 시 이메일 인증 번호를 요청하는 API")
    @GetMapping("/verify/{requestEmail}")
    public ApiResult<String> verify(@PathVariable("requestEmail") String requestEmail) throws NotFoundException {
        authService.sendVerificationMail(requestEmail);
        return success("성공적으로 인증메일을 보냈습니다.");
    }

    @ApiOperation(value = "이메일 인증", notes="회원가입 시 이메일로 전송한 인증 번호를 확인하는 API")
    @PostMapping("/verify")
    public ApiResult<String> getVerify(@RequestBody VerifyEmailDto verifyEmailDto) throws IllegalStateException{
        String permissionCode = authService.verifyEmail(verifyEmailDto);
        return success(permissionCode);
    }

    @ApiOperation(value = "(E-mail, Nickname, userId, 프로필 이미지) 반환", notes="현재 로그인한 유저의 정보(닉네임, 이메일, 회원 번호, 프로필 이미지)를 반환하는 API")
    @GetMapping("/getUserEmailNickname")
    public ApiResult<MemberDto> getEmailNickname(){
        Long userId = authService.getUserIdFromJWT();
        return success(new MemberDto(memberRepository.findById(userId).orElseThrow(()-> new NotFoundException ("존재하지 않는 회원입니다."))));
    }

    @ApiOperation(value = "프로필 이미지 변경 / 닉네임 변경", notes = "변경사항이 있는 객체의 내용을 담아서 전송을 하면 유저의 정보가 변경.")
    @PutMapping("/image/nickname")
    public ApiResult<UpdateNickNameAndImageDto> uploadProfileImage(@RequestParam(value = "image",required = false) MultipartFile image,@RequestParam(value="nickName",required = false) String nickName)
        throws Exception {
        Long userId = authService.getUserIdFromJWT();
        return success(memberService.updateProfileImageAndNickName(image,nickName,userId));
    }

    @ApiOperation(value = "비밀번호 변경", notes = "changePassword(String) : 변경하고자하는 비밀번호, currentPassword(String) : 현재 비밀번호 를 넘겨주면 validation 후 비밀번호 변경.")
    @PutMapping("/changePassword")
    public ApiResult<String> changePassword(@Valid @RequestBody ChangePasswordForm changePasswordForm){
        Long userId = authService.getUserIdFromJWT();
        memberService.changePassword(changePasswordForm, userId);
        return success("성공적으로 비밀번호를 변경했습니다.");
    }

    @ApiOperation(value = "회원탈퇴",notes = "현재 비밀번호가 일치하면 회원 탈퇴를 합니다. 이때, 예약된 자리는 없어야합니다.")
    @DeleteMapping("/deleteMember")
    public ApiResult<String> deleteMember(@RequestBody DeleteForm deleteForm, HttpServletRequest req,HttpServletResponse res){
        Long userId = authService.getUserIdFromJWT();
        memberService.deleteMember(userId,deleteForm, req, res);
        return success("회원탈퇴에 성공했습니다.");
    }

    @ApiOperation(value = "프로필 이미지 초기화",notes = "유저의 프로필 이미지를 초기화 합니다.")
    @DeleteMapping("/profileimage")
    public ApiResult<String> deleteProfileImage(){
        Long userId = authService.getUserIdFromJWT();
        memberService.deleteProfileImage(userId);
        return success("프로필 이미지가 초기화 되었습니다.");
    }

    @ApiOperation(value = "비밀번호 찾기 인증번호 발급", notes = "회원 비밀번호를 찾기 위해 이메일로 임시 인증번호를 발급한다.")
    @GetMapping("/findPassword/{requestEmail}")
    public ApiResult<String> findPassword(@PathVariable("requestEmail") String email){
        authService.sendPasswordChangeVerificationMail(email);
        return success("성공적으로 인증 메일을 전송하였습니다.");
    }

    @ApiOperation(value = "비밀번호 찾기 인증번호 검증", notes = "회원 비밀번호를 찾기 위해 이메일로 발급한 임시 인증번호를 검증한다.")
    @PostMapping("/verifyPassword")
    public ApiResult<String> validateTemporalCode(@RequestBody VerifyEmailDto verifyEmailDto) throws IllegalStateException{
        return success(authService.verifyEmail(verifyEmailDto));
    }

    @ApiOperation(value = "비밀번호 찾기 검증 후 비밀번호 변경", notes = "이메일로 본인 인증을 거친 후 바로 비밀번호를 변경할 수 있다."
        + " \n email : 비밀번호를 바꾸려는 이메일\n permissionCode : 이메일로 보낸 인증 번호를 인증하면 서버로부터 받는 코드\n password : 새로운 비밀번호")
    @PostMapping("/changeValidatedPassword")
    public ApiResult<String> changeValidatedPassword(@Valid @RequestBody ValidatedPasswordForm validatedPasswordForm) throws NotFoundException{
        return success(memberService.changeValidatedPassword(validatedPasswordForm));
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃을 한다.")
    @PostMapping("/logout")
    public ApiResult<String> logout(HttpServletRequest req, HttpServletResponse res){
        authService.deleteAllTokens(req, res);
        return success("성공적으로 로그아웃 하였습니다.");
    }
}