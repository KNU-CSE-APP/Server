package com.knu.cse.comment.dto;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "댓글 내용을 입력해주세요")
    private String content;


    public ReplyForm(Long boardId,Long commentId, String content){
        this.boardId = boardId;
        this.commentId = commentId;
        this.content= content;
    }

}
