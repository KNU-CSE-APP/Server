package com.knu.cse.comment.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CommentForm {


    @NotNull
    private Long boardId;
    @NotBlank(message = "댓글 내용을 입력해주세요")
    private String content;

    public CommentForm(Long boardId, String content){
        this.boardId = boardId;
        this.content= content;
    }
}
