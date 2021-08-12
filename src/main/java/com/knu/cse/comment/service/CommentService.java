package com.knu.cse.comment.service;

import com.knu.cse.board.domain.Board;
import com.knu.cse.board.repository.BoardRepository;
import com.knu.cse.comment.domain.Comment;
import com.knu.cse.comment.dto.CommentDto;
import com.knu.cse.comment.dto.CommentForm;
import com.knu.cse.comment.dto.ReplyForm;
import com.knu.cse.comment.repository.CommentRepository;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.errors.UnauthorizedException;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional
    public Comment writeReply(Long memberId, ReplyForm replyForm){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("회원가입된 Member가 없습니다."));
        Comment comment = commentRepository.findById(replyForm.getCommentId()).orElseThrow(() -> new NotFoundException("작성된 Comment가 없습니다."));
        return commentRepository.save(Comment.createReply(member,comment,replyForm));
    }


    @Transactional
    public void updateComment(Long memberId,Long commentId,CommentForm commentForm){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("회원가입된 Member가 없습니다."));
        Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("작성된 Comment가 존재하지 않습니다."));
        if(comment.getMember().getId()!=member.getId()) throw new UnauthorizedException("수정 권한이 없습니다");

        comment.edit(commentForm);
    }


    @Transactional
    public void deleteComment(Long memberId,Long commentId) throws UnauthorizedException{
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("회원가입된 Member가 없습니다."));
        Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("작성된 Comment가 존재하지 않습니다."));
        if(comment.getMember().getId()!=member.getId()) throw new UnauthorizedException("삭제 권한이 없습니다");

        commentRepository.deleteCommentsByIdOrParentId(commentId,commentId);
    }


    @Transactional(readOnly = true)
    public List<Comment> findMyComments(Long memId) {
        return commentRepository.findMyComments(memId).orElseThrow(()->
            new NotFoundException("작성한 댓글이 없습니다.")
        );
    }

    @Transactional(readOnly = true)
    public Comment findContentsById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->
            new NotFoundException("작성한 댓글이 없습니다.")
        );
    }

    @Transactional(readOnly = true)
    public List<CommentDto> findContentsByBoardId(Long boardId) {
        List<CommentDto> contents = commentRepository.findByBoard_Id(boardId)
            .orElseThrow(() ->
            new NotFoundException("게시물이 존재하지 않습니다."))
            .stream().map(CommentDto::new)
            .collect(Collectors.toList());

        List<CommentDto> commentList= new ArrayList<>();
        List<CommentDto> replyList = new ArrayList<>();

        for(CommentDto cur : contents){
            if(cur.getParentId()==null){
                commentList.add(cur);
            }
            else
                replyList.add(cur);
        }

        for(CommentDto reply: replyList){
            for(CommentDto comment : commentList){
                if(reply.getParentId().equals(comment.getCommentId())){
                    if(comment.getReplyList()==null) comment.allocateReplyList();
                    comment.getReplyList().add(reply);
                    break;
                }
            }
        }

        return commentList;
    }

}