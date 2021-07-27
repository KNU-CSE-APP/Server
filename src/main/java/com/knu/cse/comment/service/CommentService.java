package com.knu.cse.comment.service;

import com.knu.cse.board.domain.Board;
import com.knu.cse.board.repository.BoardRepository;
import com.knu.cse.comment.domain.Comment;
import com.knu.cse.comment.domain.CommentDTO;
import com.knu.cse.comment.domain.ReplyDTO;
import com.knu.cse.comment.repository.CommentRepository;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    /**
     * 댓글 작성하기
     * @param commentDTO -> Long parentId, String content, String author
     * @return
     */
    public Comment writeComment(CommentDTO commentDTO){
        Board board = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(() -> new NotFoundException(("작성된 Board가 없습니다.")));
        Member member = memberRepository.findById(commentDTO.getMemberId()).orElseThrow(() -> new NotFoundException(("가입된 Member가 없습니다.")));
        return commentRepository.save(Comment.setComment(member,board,commentDTO));
    }

    /**
     * 덧글 작성하기
     * @param replyDTO
     * @return
     */
    public Comment writeReply(ReplyDTO replyDTO){
        Comment comment = commentRepository.findById(replyDTO.getCommentId()).orElseThrow(() -> new NotFoundException("작성된 Comment가 없습니다."));
        Member member = memberRepository.findById(replyDTO.getMemberId()).orElseThrow(() -> new NotFoundException("회원가입된 Member가 없습니다."));
        return commentRepository.save(Comment.setReply(member,comment,replyDTO));
    }

}