package com.knu.cse.comment.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ReplyDTO {

    @NotNull
    private Long memberId;
    @NotNull
    private Long boardId;
    @NotNull
    private Long commentId;
    @NotNull
    private String content;
    @NotNull
    private String author;

    public ReplyDTO(Long memberId, Long boardId,Long commentId, String content, String author){
        this.memberId = memberId;
        this.boardId = boardId;
        this.commentId = commentId;
        this.content= content;
        this.author= author;
    }

}
