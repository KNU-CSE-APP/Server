package com.knu.cse.board.controller;


import static com.knu.cse.utils.ApiUtils.success;

import com.knu.cse.board.domain.Category;
import com.knu.cse.board.dto.BoardDto;
import com.knu.cse.board.dto.BoardForm;
import com.knu.cse.board.service.BoardService;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.errors.UnauthorizedException;
import com.knu.cse.utils.ApiUtils.ApiResult;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    private final AuthService authService;


    @ApiOperation(value = "게시물 글 작성", notes = "로그인을 한 유저가 게시물에 글을 작성 할 수 있다.")
    @PostMapping("/write")
    public ApiResult<BoardDto> writeBoard(BoardForm boardForm)
        throws NotFoundException, IOException {
        Long userId = authService.getUserIdFromJWT();
        return success(new BoardDto(boardService.writeBoard(userId, boardForm)));
    }


    @ApiOperation(value = "전체 게시물 조회", notes = "게시물의 page(0부터),size(페이지마다 보여줄 게시물 갯수),category에 맞춰서 게시물의 리스트가 전달된다.")
    @GetMapping("/list")
    public ApiResult<Page<BoardDto>> findOneBoard(@RequestParam("category") Category category,
        @RequestParam("page") Integer page,@RequestParam("size") Integer size) throws NotFoundException{
        Pageable reqPage = PageRequest.of(page,size, Sort.by("id").descending());
        return success(boardService.findAllByCategory(reqPage,category)
            .map(board-> new BoardDto(board)));
    }

    @ApiOperation(value = "게시물 기본키 글 조회", notes = "게시물 기본키로 하나의 게시물 정보를 확인할 수 있다.")
    @GetMapping("/{boardId}")
    public ApiResult<BoardDto> findOneBoard(@PathVariable("boardId") Long boardId) throws NotFoundException{
        return success(new BoardDto(boardService.findById(boardId)));
    }

    @ApiOperation(value = "게시물 기본키로 글 삭제", notes = "게시물을 작성한 사람은 게시물을 삭제 할 수 있다.")
    @DeleteMapping("/{boardId}")
    public ApiResult<String> deleteBoard(@PathVariable("boardId") Long boardId){
        try{
            Long userId = authService.getUserIdFromJWT();
            boardService.deleteBoard(userId,boardId);
            return success("게시물이 성공적으로 삭제되었습니다.");
        }
        catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        catch(UnauthorizedException e){
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @ApiOperation(value = "게시물 기본키로 글 수정", notes = "게시물을 작성한 사람은 게시물을 수정 할 수 있다. 제목이나 내용 중 수정하지 않으려는 것은 null이나 빈문자열로 보내야 한다")
    @PutMapping("/{boardId}")
    public ApiResult<String> editBoard(@PathVariable("boardId") Long boardId,BoardForm boardForm)
        throws IOException {
        try{
            Long userId = authService.getUserIdFromJWT();
            boardService.updateBoard(userId,boardId,boardForm);
            return success("게시물이 성공적으로 수정되었습니다.");
        }
        catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
        catch(UnauthorizedException e){
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @ApiOperation(value = "게시물 제목으로 글 찾기", notes = "제목으로 게시물을 검색 할 수 있다.")
    @GetMapping("/findTitle")
    public ApiResult<List<BoardDto>> findBoardByTitle(@RequestParam("title") String title) throws NotFoundException{
        return success(boardService.findByTitle(title).stream()
            .map(BoardDto::new).collect(Collectors.toList()));
    }

    @ApiOperation(value = "게시물 카테고리 글 찾기", notes = "카테고리와 일치하는 모든 게시물을 검색 할 수 있다.")
    @GetMapping("/findCategory")
    public ApiResult<List<BoardDto>> findBoardByCategory(@RequestParam("category") Category category) throws NotFoundException {
        return success(boardService.findByCategory(category).stream()
            .map(BoardDto::new).collect(Collectors.toList()));
    }

    @ApiOperation(value = "게시물 작성자(닉네임)으로 글 찾기", notes = "작성자(닉네임)으로 게시물을 검색 할 수 있다.")
    @GetMapping("/findAuthor")
    public ApiResult<List<BoardDto>>  findBoardByAuthor(@RequestParam("author") String author) throws NotFoundException{
        return success(boardService.findByAuthor(author).stream()
            .map(BoardDto::new).collect(Collectors.toList()));
    }

    @ApiOperation(value = "게시물 내용으로 글 찾기", notes = "게시물 내용으로 게시물을 검색 할 수 있다.")
    @GetMapping("/findContent")
    public ApiResult<List<BoardDto>>  findBoardByContent(@RequestParam("content") String content) throws NotFoundException{
        return success(boardService.findByContent(content).stream()
            .map(BoardDto::new).collect(Collectors.toList()));
    }

    @ApiOperation(value = "내가 작성한 게시물 조회", notes = "내가 작성한 모든 게시물을 조회한다.")
    @GetMapping("/findMyBoards")
    public ApiResult<List<BoardDto>> findMyBoards(){
        Long memId = authService.getUserIdFromJWT();
        return success(boardService.findMyBoards(memId));
    }
}
