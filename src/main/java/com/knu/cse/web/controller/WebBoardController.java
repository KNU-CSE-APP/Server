package com.knu.cse.web.controller;

import com.knu.cse.board.domain.Board;
import com.knu.cse.board.domain.Category;
import com.knu.cse.board.dto.BoardDto;
import com.knu.cse.board.dto.BoardForm;
import com.knu.cse.board.service.BoardService;
import com.knu.cse.comment.dto.CommentDto;
import com.knu.cse.comment.service.CommentService;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.errors.UnauthorizedException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class WebBoardController {


    private final BoardService boardService;
    private final CommentService commentService;

    private final AuthService authService;

    @GetMapping("/boardlist")
    public String boardList(@RequestParam(name="category") Category category, Model model){
        List<BoardDto> BoardList = boardService.findByCategory(category).stream()
            .map(BoardDto::new).collect(Collectors.toList());
        model.addAttribute("BoardMap",BoardList);
        model.addAttribute("Category",category);

        return "boardList";
    }

    @GetMapping("/board")
    public String oneBoard(@ModelAttribute(name="boardForm") BoardForm boardForm){
        return "write";
    }

    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable("id") Long boardId,Model model){
        Board board=boardService.findById(boardId);
        BoardDto boardDto = new BoardDto(boardService.findById(boardId));
        List<CommentDto> commentDto = board.getCommentList().stream().map(CommentDto::new).collect(
            Collectors.toList());

        model.addAttribute("Board",boardDto);
        model.addAttribute("Comment",commentDto);
        return "board";
    }

    @DeleteMapping("/board/{id}")
    public String deleteBoard(@PathVariable("id") Long boardId,String category){
        try{
            Long userId = authService.getUserIdFromJWT();
            boardService.deleteBoard(userId,boardId);
            return "redirect:/admin/boardlist?category="+category;
        }
        catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        catch(UnauthorizedException e){
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @DeleteMapping("/comment/{id}")
    public String deleteComment(@PathVariable("id") Long commentId,String boardId){
        try{
            Long userId = authService.getUserIdFromJWT();
            commentService.deleteComment(userId,commentId);
            return "redirect:/admin/board/"+boardId;
        }
        catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        catch(UnauthorizedException e){
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @PostMapping("/board")
    public String writeBoard(@ModelAttribute(name="boardForm") BoardForm boardForm,String category)
        throws NotFoundException, IOException {
        Long userId = authService.getUserIdFromJWT();
        boardService.writeBoard(userId,boardForm);
        return "redirect:/admin/boardlist?category="+category;
    }
}

