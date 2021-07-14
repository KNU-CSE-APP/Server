package com.knu.cse.reply.domain;

import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.comment.domain.Comment;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Reply extends BaseTimeEntity {


    @Id
    @GeneratedValue
    @Column(name="reply_id")
    private Long id;

    private String content;

    private String author;

    @ManyToOne
    @JoinColumn(name="comment_id")
    private Comment comment;

    public void setComment(Comment comment){
        if(comment.getId()!=null){
            comment.getReplyList().remove(this);
        }
        this.comment=comment;
        comment.getReplyList().add(this);
    }
}

