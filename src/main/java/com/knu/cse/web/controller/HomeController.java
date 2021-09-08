package com.knu.cse.web.controller;

import com.knu.cse.email.service.AuthService;
import com.knu.cse.email.util.CookieUtil;
import com.knu.cse.email.util.JwtUtil;
import com.knu.cse.email.util.RedisUtil;
import com.knu.cse.member.dto.SignInForm;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.model.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    @GetMapping("/home")
    public String login(@ModelAttribute SignInForm signInForm){
        try {
            Long userId = authService.getUserIdFromJWT();
        }
        catch (Exception e){
            return "login";
        }
        return "home";
    }

    @PostMapping("/home")
    public String login(@Valid @ModelAttribute(name = "signInForm") SignInForm signInForm, BindingResult bindingResult, HttpServletResponse res){
        System.out.println("signInForm.getEmail() = " + signInForm.getEmail());
        System.out.println("signInForm.getPassword() = " + signInForm.getPassword());
        System.out.println("bindingResult = " + bindingResult.toString());
        if (bindingResult.hasErrors()){
            return "login";
        }

        try {
            Member member = authService.loginUser(signInForm);
            MemberRole role = member.getRole();
            if(!(role.equals(MemberRole.ROLE_ADMIN) || role.equals(MemberRole.ROLE_MANAGER))){
                throw new IllegalStateException("허가받은 계정이 아닙니다.");
            }
            String token = jwtUtil.generateToken(member.getEmail());
            String refreshJwt = jwtUtil.generateRefreshToken(member);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, member.getEmail(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);



            return "home";
        }
        catch (Exception e){
            System.out.println(" here ");
            bindingResult.reject("loginFail",e.getMessage());
            return "login";
        }
    }

}