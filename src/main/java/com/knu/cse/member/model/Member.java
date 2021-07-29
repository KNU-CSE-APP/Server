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
import lombok.*;

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

    @Builder.Default @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<Reservation>();
//    @OneToOne(mappedBy = "member")
//    private Reservation reservation;

    @Builder.Default @OneToMany(mappedBy="member")
    private List<Board> boardList = new ArrayList<Board>();

    @Builder.Default @OneToMany(mappedBy="member")
    private List<Comment> commentList = new ArrayList<Comment>();
}