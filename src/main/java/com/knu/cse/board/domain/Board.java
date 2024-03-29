package com.knu.cse.board.domain;

import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.board.dto.BoardForm;
import com.knu.cse.comment.domain.Comment;
import com.knu.cse.image.domain.Image;
import com.knu.cse.member.model.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="board_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    private String content;

    private String author;

    @OneToMany(mappedBy="board",cascade=CascadeType.ALL)
    private List<Image> imageList;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy="board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<Comment>();

    public void edit(BoardForm boardForm,List<String> deleteUrlList){
        title=changedInfo(title,boardForm.getTitle());
        content = changedInfo(content, boardForm.getContent());
        if(deleteUrlList!=null){
            this.imageList = imageList.stream()
                .filter(o -> deleteUrlList.stream().noneMatch(target -> target.equals(o.getUrl())))
                .collect(Collectors.toList());
        }
    }

    private String changedInfo(String original, String changed){
        return (changed == null || changed.equals("")) ? original : changed;
    }

    public void setMember(Member member){
        if(this.member!=null){
            this.member.getBoardList().remove(this);
        }
        this.member=member;
        member.getBoardList().add(this);
    }

    public Board (Member member, BoardForm boardForm){
        this.author = member.getNickname();
        this.category = boardForm.getCategory();
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
        this.imageList=new ArrayList<>();
        this.setMember(member);
    }

    public void deleteMember(){
        this.member = null;
        this.author = "탈퇴한 회원입니다.";
    }

    public void updateAuthor(String newAuthor){
        this.author=changedInfo(this.author,newAuthor);
    }

}
