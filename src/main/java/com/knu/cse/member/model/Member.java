package com.knu.cse.member.model;

import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.board.domain.WriteBoard;
import com.knu.cse.comment.domain.WriteComment;
import java.util.List;
import javax.persistence.*;

import com.knu.cse.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Member extends BaseTimeEntity {

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
    private MemberRole role = MemberRole.ROLE_NOT_PERMITTED;


    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations;

    @OneToMany(mappedBy="member")
    private List<WriteBoard> boardList;

    @OneToMany(mappedBy="member")
    private List<WriteComment> commentList;


}
