package com.knu.cse.board.domain;



import com.knu.cse.base.BaseTimeEntity;
import com.knu.cse.member.model.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class WriteBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="write_board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;


    public void setMember(Member member){
        if(member.getId()!=null){
            this.member.getBoardList().remove(this);
        }
        this.member=member;
        member.getBoardList().add(this);
    }

    public void setBoard(Board board){
        if(board.getId()!=null){
            this.board.getWriterList().remove(this);
        }
        this.board=board;
        board.getWriterList().add(this);
    }
}
