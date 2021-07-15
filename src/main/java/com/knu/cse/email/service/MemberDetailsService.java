package com.knu.cse.email.service;

import com.knu.cse.member.model.Member;
import com.knu.cse.member.security.SecurityMember;
import com.knu.cse.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);
        if(email == null){
            throw new UsernameNotFoundException(email + " : 사용자 존재하지 않음");
        }

        return new SecurityMember(member);
    }
}
