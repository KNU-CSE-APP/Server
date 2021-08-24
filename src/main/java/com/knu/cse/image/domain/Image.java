package com.knu.cse.image.domain;


import com.knu.cse.board.domain.Board;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import lombok.Getter;


@Entity
@NoArgsConstructor
@Getter
public class Image {

    @Id
    @GeneratedValue
    @Column(name="image_id")
    private Long id;

    private String fileName;

    private String url;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    public Image(String url,Board board){
        this.url=url;
        setBoard(board);
    }


    public void setBoard(Board board){
        if(this.board!=null){
            this.board.getImageList().remove(this);
        }
        this.board=board;
        board.getImageList().add(this);
    }
}
