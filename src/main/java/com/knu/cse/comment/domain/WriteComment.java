package com.knu.cse.comment.domain;

import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.member.model.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class WriteComment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="write_comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    public void setMember(Member member){
        if(member.getId()!=null){
            this.member.getCommentList().remove(this);
        }
        this.member=member;
        member.getCommentList().add(this);
    }

    public void setComment(Comment comment){
        if(comment.getId()!=null){
            this.comment.getCommenterList().remove(this);
        }
        this.comment=comment;
        comment.getCommenterList().add(this);
    }

}
