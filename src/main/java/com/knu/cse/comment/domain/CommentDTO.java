package com.knu.cse.comment.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CommentDTO {

    @NotNull
    private Long memberId;
    @NotNull
    private Long boardId;
    @NotNull
    private String content;
    @NotNull
    private String author;

    public CommentDTO(Long memberId, Long boardId, String content, String author){
        this.memberId = memberId;
        this.boardId = boardId;
        this.content= content;
        this.author= author;
    }
}
