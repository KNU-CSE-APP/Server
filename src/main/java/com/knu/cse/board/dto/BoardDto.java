package com.knu.cse.board.dto;

import com.knu.cse.board.domain.Board;
import com.knu.cse.board.domain.Category;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardDto {

    private Long boardId;

    private Category category;

    private String title;

    private String content;

    private String author;

    private LocalDateTime dateTime;

    public BoardDto(){}

    public BoardDto(Board board){
        this.boardId= board.getId();
        this.category=board.getCategory();
        this.title=board.getTitle();
        this.content=board.getContent();
        this.author=board.getAuthor();
        this.dateTime=board.getCreatedDate();
    }

}
