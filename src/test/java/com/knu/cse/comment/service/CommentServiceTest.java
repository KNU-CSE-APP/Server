package com.knu.cse.comment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
class CommentServiceTest {

//    @Autowired private AuthService authService;
//    @Autowired private CommentService commentService;
//    @Autowired private BoardRepository boardRepository;
//    @Autowired private MemberRepository memberRepository;
//
//
//    @Test
//    @Rollback(value=true)
//    public void 댓글작성테스트(){
//        SignUpForm signUpForm1 = new SignUpForm("Girin", "nun2580@knu.ac.kr", "samsung159!", "김기현", "2016113934", Gender.MALE, Major.ADVANCED);
//        SignUpForm signUpForm2 = new SignUpForm("Daegu", "k3832580@knu.ac.kr", "samsung159!", "김기현222", "2016113934", Gender.FEMALE, Major.GLOBAL);
//
//        Board saveBoard = Board.builder()
//                .category(Category.FREE)
//                .title("타이틀")
//                .content("내용")
//                .author("글쓴이")
//                .build();
//
//        Member member1 = authService.signUpMember(signUpForm1);
//        Member member2 = authService.signUpMember(signUpForm2);
//        Board board = boardRepository.save(saveBoard);
//
//        CommentDTO commentDTO = new CommentDTO(member1.getId(),board.getId(), "댓글 내용", "댓글 저자");
//        Comment comment = commentService.writeComment(commentDTO);
//        System.out.println("comment.getId() = " + comment.getId());
//        System.out.println("comment.getContent() = " + comment.getContent());
//        System.out.println("comment.getAuthor() = " + comment.getAuthor());
//        System.out.println("comment.getParentId() = " + comment.getParentId());
//        System.out.println("comment.getMember() = " + comment.getMember());
//    }
//
//    @Test
//    @Rollback(value = true)
//    public void 덧글작성테스트(){
//        SignUpForm signUpForm1 = new SignUpForm("Girin", "nun2580@knu.ac.kr", "samsung159!", "김기현", "2016113934", Gender.MALE, Major.ADVANCED);
//        SignUpForm signUpForm2 = new SignUpForm("Daegu", "k3832580@knu.ac.kr", "samsung159!", "김기현222", "2016113934", Gender.FEMALE, Major.GLOBAL);
//
//        Board saveBoard = Board.builder()
//                .category(Category.FREE)
//                .title("타이틀")
//                .content("내용")
//                .author("글쓴이")
//                .build();
//
//        Member member1 = authService.signUpMember(signUpForm1);
//        Member member2 = authService.signUpMember(signUpForm2);
//        Board board = boardRepository.save(saveBoard);
//
//        CommentDTO commentDTO = new CommentDTO(member1.getId(),board.getId(), "댓글 내용", "댓글 저자");
//        Comment comment = commentService.writeComment(commentDTO);
//        System.out.println("comment.getId() = " + comment.getId());
//        System.out.println("comment.getContent() = " + comment.getContent());
//        System.out.println("comment.getAuthor() = " + comment.getAuthor());
//        System.out.println("comment.getParentId() = " + comment.getParentId());
//        System.out.println("comment.getMember() = " + comment.getMember());
//
//        ReplyDTO replyDTO = new ReplyDTO(member1.getId(),board.getId(), comment.getId(), "덧글 내용", "덧글 작성자");
//        Comment reply = commentService.writeReply(replyDTO);
//        System.out.println("reply.getId() = " + reply.getId());
//        System.out.println("reply.getParentId() = " + reply.getParentId());
//        System.out.println("reply.getAuthor() = " + reply.getAuthor());
//        System.out.println("reply.getMember() = " + reply.getMember());
//        System.out.println("reply.getBoard() = " + reply.getBoard());
//    }

}