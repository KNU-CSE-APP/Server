package com.knu.cse.Comment.domain;

import com.knu.cse.Board.domain.Board;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    private String content;

    private LocalDateTime date;

    private String author;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @OneToMany
    @JoinColumn(name="comment_id")
    private List<WriteComment> commenterList;

    public void setBoard(Board board){
        if(board.getId()!=null){
            board.getCommentList().remove(this);
        }
        this.board=board;
        board.getCommentList().add(this);
    }
}
