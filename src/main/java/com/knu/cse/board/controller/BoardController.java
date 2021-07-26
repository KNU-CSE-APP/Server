package com.knu.cse.board.controller;


import static com.knu.cse.utils.ApiUtils.success;

import com.knu.cse.board.domain.Category;
import com.knu.cse.board.dto.BoardDto;
import com.knu.cse.board.dto.BoardForm;
import com.knu.cse.board.service.BoardService;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.utils.ApiUtils.ApiResult;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    private final AuthService authService;


    @PostMapping("/write")
    public ApiResult<BoardDto> writeBoard(@RequestBody BoardForm boardForm, HttpServletRequest req)
        throws NotFoundException {
        Long userId = authService.getUserIdFromJWT(req);
        return success(new BoardDto(boardService.writeBoard(userId, boardForm)));
    }

    @GetMapping("/{boardId}")
    public ApiResult<BoardDto> findOneBoard(@PathVariable("boardId") Long boardId) throws NotFoundException{
        return success(new BoardDto(boardService.findById(boardId)));
    }

    @GetMapping("/findTitle")
    public ApiResult<List<BoardDto>> findBoardByTitle(@RequestParam("title") String title) throws NotFoundException{
        return success(boardService.findByTitle(title).stream()
            .map(BoardDto::new).collect(Collectors.toList()));
    }

    @GetMapping("/findCategory")
    public ApiResult<List<BoardDto>> findBoardByCategory(@RequestParam("category") Category category) throws NotFoundException {
        return success(boardService.findByCategory(category).stream()
            .map(BoardDto::new).collect(Collectors.toList()));
    }

    @GetMapping("/findAuthor")
    public ApiResult<List<BoardDto>>  findBoardByAuthor(@RequestParam("author") String author) throws NotFoundException{
        return success(boardService.findByAuthor(author).stream()
            .map(BoardDto::new).collect(Collectors.toList()));
    }

    @GetMapping("/findContent")
    public ApiResult<List<BoardDto>>  findBoardByContent(@RequestParam("content") String content) throws NotFoundException{
        return success(boardService.findByContent(content).stream()
            .map(BoardDto::new).collect(Collectors.toList()));
    }

}
