package com.knu.cse.comment.dto;

import com.knu.cse.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.Getter;


@Getter
public class ReplyDto {


    private Long commentId;

    private Long replyId;

    private String author;

    private String content;

    private LocalDateTime time;

    ReplyDto(Comment reply){
        this.commentId = reply.getParentId();
        this.replyId=reply.getId();
        this.author=reply.getAuthor();
        this.content=reply.getContent();
        this.time=reply.getLastModifiedDate();
    }

}
