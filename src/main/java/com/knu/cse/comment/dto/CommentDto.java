package com.knu.cse.comment.dto;

import com.knu.cse.comment.domain.Comment;
import com.knu.cse.member.model.Member;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CommentDto {


    private Long boardId;

    private Long commentId;

    private Long parentId;

    private String author;

    private String content;

    private String time;

    private String profileImage;

    private List<CommentDto> replyList;

    public CommentDto(Comment comment){
        this.boardId=comment.getBoard().getId();
        this.commentId = comment.getId();
        this.author=comment.getAuthor();
        this.content=comment.getContent();
        this.time=comment.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.parentId=comment.getParentId();

        Member member = comment.getMember();
        if(member==null) this.profileImage=null;
        else
            this.profileImage=member.getProfileImageUrl();
    }

    public void allocateReplyList() {this.replyList=new ArrayList<>();}
    public void allocateReplyList(List<CommentDto> replyList) {this.replyList= replyList;}
}
