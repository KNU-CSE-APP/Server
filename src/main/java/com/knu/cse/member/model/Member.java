package com.knu.cse.member.model;

import com.knu.cse.base.BaseEntity;
import com.knu.cse.board.domain.Board;
import com.knu.cse.board.dto.BoardForm;
import com.knu.cse.comment.domain.Comment;
import com.knu.cse.reservation.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String username;
    private String nickname;
    private String studentId;
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Major major;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.ROLE_NOT_PERMITTED;

    public void changeRole(MemberRole memberRole){
        this.role = memberRole;
    }

    @OneToOne(mappedBy = "member")
    private Reservation reservation;

    @OneToMany(mappedBy="member")
    private List<Board> boardList = new ArrayList<Board>();

    @OneToMany(mappedBy="member")
    private List<Comment> commentList = new ArrayList<Comment>();

    public void setReservation(Reservation reservation) {
        reservation.setMember(this);
        this.reservation = reservation;
    }


    @Builder
    public Member(String email, String password, String username, String nickname,
        String studentId, String profileImageUrl, Gender gender, Major major,
        MemberRole role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.studentId = studentId;
        this.profileImageUrl = profileImageUrl;
        this.gender = gender;
        this.major = major;
        this.role = role;
    }

    public void changeProfileImage(String imagePath){
        this.profileImageUrl = imagePath;
    }
  
    public void changePassword(String password){this.password = password;}

    public void changeNickName(String nickname){
        this.nickname=nickname;
    }

    public void deleteProfileImage(){this.profileImageUrl=null;}

    public void deleteMember(){
        this.email = null;
        this.password = null;
        this.username = null;
        this.nickname = null;
        this.studentId = null;
        this.profileImageUrl = null;
        this.gender = null;
        this.major = null;
        this.role = null;
    }
}