package com.knu.cse.comment.domain;

import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.board.domain.Board;
import com.knu.cse.comment.dto.CommentForm;
import com.knu.cse.comment.dto.ReplyForm;
import com.knu.cse.member.model.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    private String content;

    private String author;

    @Setter
    private Long parentId;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

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

    public static Comment createComment(Member member,Board board, CommentForm commentForm){
        Comment comment = Comment.builder()
                .content(commentForm.getContent())
                .author(commentForm.getAuthor())
                .build();
        comment.setBoard(board);
        comment.setMember(member);
        return comment;
    }

    public static Comment createReply(Member member,Comment comment, ReplyForm replyForm){
        Comment reply = Comment.builder()
                .content(replyForm.getContent())
                .author(replyForm.getAuthor())
                .build();
        reply.setParentId(comment.id);
        reply.setMember(member);
        reply.setBoard(comment.getBoard());
        return reply;
    }
}
