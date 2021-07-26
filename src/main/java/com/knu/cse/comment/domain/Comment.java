package com.knu.cse.comment.domain;

import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.board.domain.Board;
import com.knu.cse.member.model.Member;
import com.knu.cse.reply.domain.Reply;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    private String content;

    private String author;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replyList;

    public void setMember(Member member){
        if(this.member!=null){
            this.member.getCommentList().remove(this);
        }
        this.member=member;
        member.getCommentList().add(this);
    }

    public void setBoard(Board board){
        if(this.board!=null){
            this.board.getCommentList().remove(this);
        }
        this.board=board;
        board.getCommentList().add(this);
    }
}
