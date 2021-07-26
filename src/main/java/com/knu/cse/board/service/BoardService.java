package com.knu.cse.board.service;

import com.knu.cse.board.domain.Board;
import com.knu.cse.board.domain.Category;
import com.knu.cse.board.dto.BoardForm;
import com.knu.cse.board.repository.BoardRepository;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Board writeBoard(Long userId, BoardForm boardForm){
        Member member = memberRepository.findById(userId).orElseThrow(
            ()-> new NotFoundException("해당 Member를 찾을 수 없습니다"));

        Board board = Board.createBoard(member,boardForm);
        boardRepository.save(board);

        return board;
    }

    @Transactional(readOnly = true)
    public Board findById(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(
            ()-> new NotFoundException(("해당 Board를 찾을 수 없습니다")));
    }

    @Transactional(readOnly = true)
    public List<Board> findByTitle(String title){
        Optional<List<Board>> byTitle = boardRepository.findByTitle(title);
        if(byTitle.get().size()==0) throw new NotFoundException("Title : "+title+"에 해당하는 Board를 찾을 수 없습니다");

        return byTitle.get();
    }

    @Transactional(readOnly = true)
    public List<Board> findByAuthor(String author){
        Optional<List<Board>> byAuthor = boardRepository.findByAuthor(author);
        if(byAuthor.get().size()==0) throw new NotFoundException("Author :"+author+" 해당하는 Board를 찾을 수 없습니다");

        return byAuthor.get();
    }

    @Transactional(readOnly = true)
    public List<Board> findByCategory(Category category){
        Optional<List<Board>> byCategory = boardRepository.findByCategory(category);
        if(byCategory.get().size()==0) throw new NotFoundException("Category :" +category+" 해당하는 Board를 찾을 수 없습니다");

        return byCategory.get();
    }

    @Transactional(readOnly = true)
    public List<Board> findByContent(String content){
        Optional<List<Board>> byContent = boardRepository.findByContent(content);
        if(byContent.get().size()==0) throw new NotFoundException("Content :"+content+" 해당하는 Board를 찾을 수 없습니다");

        return byContent.get();
    }

    public void deleteBoard(Long boardId){
        boardRepository.deleteById(boardId);
    }

    public void updateBoard(Long userId,Long boardId,BoardForm boardForm){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("해당 Board를 찾을 수 없습니다"));
        board.edit(boardForm);
    }

}
