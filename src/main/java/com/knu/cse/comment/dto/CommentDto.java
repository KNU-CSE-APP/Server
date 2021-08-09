package com.knu.cse.comment.dto;

import com.knu.cse.comment.domain.Comment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class CommentDto {


    private Long boardId;

    private Long commentId;

    private Long parentId;

    private String author;

    private String content;

    private String time;

    private Boolean valid;

    public CommentDto(Comment comment){
        this.boardId=comment.getBoard().getId();
        this.commentId = comment.getId();
        this.author=comment.getAuthor();
        this.content=comment.getContent();
        this.time=comment.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.parentId=comment.getParentId();
        this.valid=comment.getMember().getValid();
    }

}
