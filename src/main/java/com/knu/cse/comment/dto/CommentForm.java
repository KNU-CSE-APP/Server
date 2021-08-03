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

    public CommentForm(Long boardId, String content){
        this.boardId = boardId;
        this.content= content;
    }
}
