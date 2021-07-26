package com.knu.cse.member.dto;

import com.knu.cse.member.model.Member;
import lombok.Data;

@Data
public class MemberDto {

    private Long userId;
    private String email;
    private String nickname;

    public MemberDto(Member member) {
        this.userId = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}
