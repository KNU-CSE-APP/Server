package com.knu.cse.board.dto;

import com.knu.cse.board.domain.Board;
import com.knu.cse.board.domain.Category;
import com.knu.cse.member.model.Member;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class BoardDto {

    private Long boardId;

    private Category category;

    private String title;

    private String content;

    private String author;

    private String time;

    private String profileImg;

    private Integer commentCnt;

    public BoardDto(){}

    public BoardDto(Board board){
        this.boardId= board.getId();
        this.category=board.getCategory();
        this.title=board.getTitle();
        this.content=board.getContent();
        this.author=board.getAuthor();
        this.time=board.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.commentCnt=board.getCommentList().size();

        Member member = board.getMember();
        if(board.getMember()==null) this.profileImg=null;
        else
            this.profileImg=member.getProfileImageUrl();
    }

}
