package com.knu.cse.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .mvcMatchers("/**", "/swagger-ui.html", "/user/**").permitAll()
            .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll()
            .anyRequest().authenticated();

        http.cors().and();
        http.csrf().disable();
        //http.formLogin()
        //    .loginPage("/signIn").permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .mvcMatchers("/node_modules/**")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
