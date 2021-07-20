package com.knu.cse.member.controller;

import com.knu.cse.email.util.CookieUtil;
import com.knu.cse.email.util.JwtUtil;
import com.knu.cse.email.util.RedisUtil;
import com.knu.cse.member.model.Member;
import com.knu.cse.ApiResult.Response;
import com.knu.cse.member.dto.RequestVerifyEmail;
import com.knu.cse.member.dto.SignInForm;
import com.knu.cse.member.dto.SignUpForm;
import com.knu.cse.email.service.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class MemberController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    @PostMapping("/signIn")
    public Response signIn(@Valid @RequestBody SignInForm signInForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            final Member member = authService.loginUser(signInForm);
            final String token = jwtUtil.generateToken(member.getEmail());
            final String refreshJwt = jwtUtil.generateRefreshToken(member);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, member.getEmail(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            return new Response("success", "로그인에 성공했습니다.", token);
        } catch (Exception e) {
            return new Response("error", "로그인에 실패했습니다.", e.getMessage());
        }
    }

    @PostMapping("/signUp")
    public Response signUpSubmit(@Valid @RequestBody SignUpForm signUpForm, Errors errors) {
        Response response = new Response();

        try{
            Member savedMember = authService.signUpMember(signUpForm);
            response.setResponse("success");
            response.setMessage("회원가입을 성공적으로 완료했습니다.");
            authService.sendVerificationMail(savedMember);
        }
        catch(Exception e){
            response.setResponse("failed");
            response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @PostMapping("/verify")
    public Response verify(@RequestBody RequestVerifyEmail requestVerifyEmail, HttpServletRequest req, HttpServletResponse res) {
        Response response;
        try {
            Member member = authService.findByEmail(requestVerifyEmail.getEmail());
            authService.sendVerificationMail(member);
            response = new Response("success", "성공적으로 인증메일을 보냈습니다.", null);
        } catch (Exception exception) {
            response = new Response("error", "인증메일을 보내는데 문제가 발생했습니다.", exception);
        }
        return response;
    }

    @GetMapping("/verify/{key}")
    public Response getVerify(@PathVariable String key) {
        Response response;
        try {
            authService.verifyEmail(key);
            response = new Response("success", "성공적으로 인증메일을 확인했습니다.", null);

        } catch (Exception e) {
            response = new Response("error", "인증메일을 확인하는데 실패했습니다.", null);
        }
        return response;
    }

    @GetMapping("test")
    public String getUserEmail(HttpServletRequest req, HttpServletResponse res){
        return authService.getEmailFromJWT(req, res);
    }
}
