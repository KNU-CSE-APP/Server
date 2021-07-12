package com.knu.cse.Board.domain;


import com.knu.cse.Member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class WriteBoard {

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
            member.getBoardList().remove(this);
        }
        this.member=member;
        member.getBoardList().add(this);
    }

    public void setBoard(Board board){
        if(board.getId()!=null){
            board.getWritenUserList().remove(this);
        }
        this.board=board;
        board.getWritenUserList().add(this);
    }
}
