package com.knu.cse.member.model;

import com.knu.cse.base.BaseEntity;
import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.board.domain.Board;
import com.knu.cse.comment.domain.Comment;
import com.knu.cse.reservation.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
//import com.knu.cse.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
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
    @Builder.Default private MemberRole role = MemberRole.ROLE_NOT_PERMITTED;

    public void changeRole(MemberRole memberRole){
        this.role = memberRole;
    }

    @OneToOne(mappedBy = "member")
    private Reservation reservation;

    @Builder.Default @OneToMany(mappedBy="member")
    private List<Board> boardList = new ArrayList<Board>();

    @Builder.Default @OneToMany(mappedBy="member")
    private List<Comment> commentList = new ArrayList<Comment>();


    public void setReservation(Reservation reservation) {
        reservation.setMember(this);
        this.reservation = reservation;
    }


    public void changeProfileImage(String imagePath){
        this.profileImageUrl = imagePath;
    }

    public void changePassword(String password){this.password = password;}
}