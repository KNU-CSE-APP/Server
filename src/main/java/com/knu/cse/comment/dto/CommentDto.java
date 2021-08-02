package com.knu.cse.comment.dto;

import com.knu.cse.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentDto {

    private Long commentId;

    private String author;

    private String content;

    private LocalDateTime time;

    public CommentDto(Comment comment){
        this.commentId = comment.getId();
        this.author=comment.getAuthor();
        this.content=comment.getContent();
        this.time=comment.getLastModifiedDate();
    }

}
