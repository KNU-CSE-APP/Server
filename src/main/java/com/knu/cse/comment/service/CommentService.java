package com.knu.cse.comment.service;

import com.knu.cse.board.domain.Board;
import com.knu.cse.board.repository.BoardRepository;
import com.knu.cse.comment.domain.Comment;
import com.knu.cse.comment.dto.CommentForm;
import com.knu.cse.comment.dto.ReplyForm;
import com.knu.cse.comment.repository.CommentRepository;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    /**
     * 댓글 작성하기
     * @param commentForm -> Long boardId, String content, String author
     * @return
     */
    public Comment writeComment(Long memberId,CommentForm commentForm){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(("가입된 Member가 없습니다.")));
        Board board = boardRepository.findById(commentForm.getBoardId()).orElseThrow(() -> new NotFoundException(("작성된 Board가 없습니다.")));
        return commentRepository.save(Comment.createComment(member,board,commentForm));
    }

    /**
     * 덧글 작성하기
     * @param replyForm
     * @return
     */
    public Comment writeReply(Long memberId, ReplyForm replyForm){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("회원가입된 Member가 없습니다."));
        Comment comment = commentRepository.findById(replyForm.getCommentId()).orElseThrow(() -> new NotFoundException("작성된 Comment가 없습니다."));
        return commentRepository.save(Comment.createReply(member,comment,replyForm));
    }

}