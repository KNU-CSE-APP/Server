package com.knu.cse.web.controller;

import com.knu.cse.board.domain.Category;
import com.knu.cse.board.dto.BoardDto;
import com.knu.cse.board.dto.BoardForm;
import com.knu.cse.board.service.BoardService;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.errors.NotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class WebBoardController {


    private final BoardService boardService;

    private final AuthService authService;

    @GetMapping("/boardlist")
    public String boardList(Model model){
        List<BoardDto> BoardList = boardService.findByCategory(Category.ADMIN).stream()
            .map(BoardDto::new).collect(Collectors.toList());
        model.addAttribute("BoardMap",BoardList);

        return "boardList";
    }

    @GetMapping("/board")
    public String oneBoard(@ModelAttribute(name="boardForm") BoardForm boardForm){
        return "write";
    }

    @PostMapping("/board")
    public String writeBoard(@ModelAttribute(name="boardForm") BoardForm boardForm)
        throws NotFoundException, IOException {
        Long userId = authService.getUserIdFromJWT();
        boardService.writeBoard(userId,boardForm);
        return "redirect:/admin/boardlist";
    }
}
