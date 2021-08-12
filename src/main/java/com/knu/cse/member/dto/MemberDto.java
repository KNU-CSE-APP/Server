package com.knu.cse.member.dto;

import com.knu.cse.member.model.Member;
import lombok.Data;

@Data
public class MemberDto {

    private Long userId;
    private String email;
    private String nickname;
    private String imagePath;
    private String username;
    private String studentId;

    public MemberDto(Member member) {
        this.userId = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.imagePath = member.getProfileImageUrl();
        this.username = member.getUsername();
        this.studentId = member.getStudentId();
    }
}
