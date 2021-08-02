package com.knu.cse.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CommentForm {


    @NotNull
    private Long boardId;
    @NotNull
    private String content;
    @NotNull
    private String author;

    public CommentForm(Long boardId, String content, String author){
        this.boardId = boardId;
        this.content= content;
        this.author= author;
    }
}
