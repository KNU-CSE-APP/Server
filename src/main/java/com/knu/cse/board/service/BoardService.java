package com.knu.cse.board.service;

import com.knu.cse.board.domain.Board;
import com.knu.cse.board.domain.Category;
import com.knu.cse.board.dto.BoardDto;
import com.knu.cse.board.dto.BoardForm;
import com.knu.cse.board.repository.BoardRepository;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.errors.UnauthorizedException;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public Page<Board> findAllByCategory(Pageable reqPage,Category category){
        return boardRepository.findAllByCategory(reqPage,category);
    }

    public Board writeBoard(Long userId, BoardForm boardForm){
        Member member = memberRepository.findById(userId).orElseThrow(
            ()-> new NotFoundException("해당 Member를 찾을 수 없습니다"));

        Board board = new Board(member, boardForm);
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
        Optional<List<Board>> byTitle = Optional.ofNullable(boardRepository.findByTitle(title.trim()).orElseThrow(
                () -> new NotFoundException("Title : " + title + "에 해당하는 Board를 찾을 수 없습니다")
        ));

        return byTitle.get();
    }

    @Transactional(readOnly = true)
    public List<Board> findByAuthor(String author){
        Optional<List<Board>> byAuthor = Optional.ofNullable(boardRepository.findByAuthor(author.trim()).orElseThrow(
                () -> new NotFoundException("Author :" + author + " 해당하는 Board를 찾을 수 없습니다")
        ));

        return byAuthor.get();
    }

    @Transactional(readOnly = true)
    public List<Board> findByCategory(Category category){
        Optional<List<Board>> byCategory = Optional.ofNullable(boardRepository.findByCategory(category).orElseThrow(
                () -> new NotFoundException("Category :" + category + " 해당하는 Board를 찾을 수 없습니다")
        ));

        return byCategory.get();
    }

    @Transactional(readOnly = true)
    public List<Board> findByContent(String content){
        Optional<List<Board>> byContent = Optional.ofNullable(boardRepository.findByContent(content.trim()).orElseThrow(
                () -> new NotFoundException("Content :" + content + " 해당하는 Board를 찾을 수 없습니다")
        ));

        return byContent.get();
    }


    public void deleteBoard(Long userId,Long boardId){
        Member member = memberRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 Member를 찾을 수 없습니다"));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("해당 Board를 찾을 수 없습니다"));

        if(board.getMember().getId()!=member.getId()) throw new UnauthorizedException("게시물 삭제 권한이 없습니다");
        boardRepository.deleteById(boardId);
    }


    public void updateBoard(Long userId,Long boardId,BoardForm boardForm){
        Member member = memberRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 Member를 찾을 수 없습니다"));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("해당 Board를 찾을 수 없습니다"));

        if(board.getMember().getId()!=member.getId()) throw new UnauthorizedException("게시물 수정 권한이 없습니다");
        board.edit(boardForm);
    }

    @Transactional(readOnly = true)
    public List<BoardDto> findMyBoards(Long id){
        return boardRepository.findByMember_Id(id).orElseThrow(()->
            new NotFoundException("존재하지 않는 회원입니다."))
            .stream().map(BoardDto::new).collect(Collectors.toList());
    }


    public void updateBoardAuthor(Member member,String newNickName){
        member.getBoardList().stream().forEach(o->o.updateAuthor(newNickName));
    }
}
