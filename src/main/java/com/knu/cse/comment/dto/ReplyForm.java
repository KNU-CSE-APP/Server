package com.knu.cse.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ReplyForm {

    @NotNull
    private Long boardId;
    @NotNull
    private Long commentId;
    @NotNull
    private String content;
    @NotNull
    private String author;

    public ReplyForm(Long boardId,Long commentId, String content, String author){
        this.boardId = boardId;
        this.commentId = commentId;
        this.content= content;
        this.author= author;
    }

}
