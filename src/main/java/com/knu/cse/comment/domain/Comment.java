package com.knu.cse.comment.domain;

import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.board.domain.Board;
import com.knu.cse.comment.dto.CommentForm;
import com.knu.cse.comment.dto.ReplyForm;
import com.knu.cse.member.model.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;
import org.hibernate.annotations.Fetch;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    private String content;

    private String author;

    @Setter
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Builder
    public Comment(String content, String author){
        this.content = content;
        this.author = author;
    }

    public static Comment createComment(Member member,Board board, CommentForm commentForm){
        Comment comment = Comment.builder()
                .content(commentForm.getContent())
                .author(member.getNickname())
                .build();
        comment.setBoard(board);
        comment.setMember(member);
        return comment;
    }

    public static Comment createReply(Member member,Comment comment, ReplyForm replyForm){
        Comment reply = Comment.builder()
                .content(replyForm.getContent())
                .author(member.getNickname())
                .build();
        reply.setParentId(comment.id);
        reply.setMember(member);
        reply.setBoard(comment.getBoard());
        return reply;
    }

    public void edit(CommentForm commentForm){
        content = changedInfo(content, commentForm.getContent());
    }

    private String changedInfo(String original, String changed){
        return (changed == null || changed.equals("")) ? original : changed;
    }

    public void deleteMember(){
        this.member = null;
        this.author = "탈퇴한 회원입니다.";
    }

    public void updateAuthor(String newAuthor){
        this.author=changedInfo(this.author,newAuthor);
    }

}
