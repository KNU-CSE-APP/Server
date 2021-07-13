package com.knu.cse.domain;

import com.knu.cse.Board.domain.WriteBoard;
import com.knu.cse.Comment.domain.WriteComment;
import com.knu.cse.domain.userConfig.Gender;
import com.knu.cse.domain.userConfig.Major;
import com.knu.cse.domain.userConfig.UserRole;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String studentId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Major major;

    private boolean emailVerified;
    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGeneratedAt;
    private LocalDateTime joinedAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_NOT_PERMITTED;


    @OneToMany(mappedBy="member")
    private List<WriteBoard> boardList;

    @OneToMany(mappedBy="member")
    private List<WriteComment> commentList;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }
}
