package com.knu.cse.comment.controller;

import static com.knu.cse.utils.ApiUtils.success;

import com.knu.cse.comment.dto.CommentDto;
import com.knu.cse.comment.dto.CommentForm;
import com.knu.cse.comment.dto.ReplyForm;
import com.knu.cse.comment.service.CommentService;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.errors.UnauthorizedException;
import com.knu.cse.utils.ApiUtils.ApiResult;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private final AuthService authService;

    @GetMapping("/{commentId}")
    public ApiResult<CommentDto> getComment(@RequestParam("commentId") Long commentId){
        return success(new CommentDto(commentService.findContentsById(commentId)));
    }

    @PostMapping("/write")
    public ApiResult<CommentDto> writeComment(@RequestBody CommentForm commentForm,
        HttpServletRequest req) throws NotFoundException {
        Long memberId = authService.getUserIdFromJWT();

        return success(new CommentDto(commentService.writeComment(memberId, commentForm)));
    }

    @PostMapping("/reply/write")
    public ApiResult<CommentDto> writeReply(@RequestBody ReplyForm replyForm,
        HttpServletRequest req) throws NotFoundException {
        Long memberId = authService.getUserIdFromJWT();

        return success(new CommentDto(commentService.writeReply(memberId, replyForm)));
    }

    @ApiOperation(value = "내가 쓴 댓글 조회", notes = "내가 쓴 댓글을 모두 조회한다.")
    @GetMapping("/getAllComments")
    public ApiResult<List<CommentDto>> getMyAllWrite(HttpServletRequest req) throws NotFoundException {
        Long memberId = authService.getUserIdFromJWT();
        return success(commentService.findMyComments(memberId).stream().map(CommentDto::new)
            .collect(Collectors.toList()));
    }

    @ApiOperation(value = "특정 게시판의 댓글 조회", notes = "게시판 아이디를 통해 특정 게시판의 댓글을 조회한다.")
    @GetMapping("/findContentsByBoardId")
    public ApiResult<List<CommentDto>> findContentsByBoardId(Long boardId){
        return success(commentService.findContentsByBoardId(boardId));
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글 아이디를 통해 특정 댓글을 수정한다.")
    @PutMapping("/{commentId}")
    public ApiResult<String> editComment(@PathVariable("commentId") Long commentId, @RequestBody CommentForm commentForm,
        HttpServletRequest req) {
        try {
            Long memberId = authService.getUserIdFromJWT();
            commentService.updateComment(memberId,commentId,commentForm);

            return success("수정이 완료되었습니다.");
        }
        catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글 아이디를 통해 특정 댓글을 삭제한다(답글을 포함)")
    @DeleteMapping("/{commentId}")
    public ApiResult<String> deleteComment(@PathVariable("commentId")Long commentId,
        HttpServletRequest req){

        try {
            Long memberId = authService.getUserIdFromJWT();
            commentService.deleteComment(memberId,commentId);

            return success("댓글 삭제가 완료되었니다.");
        }
        catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        catch(UnauthorizedException e){
            throw new UnauthorizedException(e.getMessage());
        }
    }
}

