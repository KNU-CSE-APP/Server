package com.knu.cse.member.service;

import com.knu.cse.board.domain.Board;
import com.knu.cse.board.service.BoardService;
import com.knu.cse.comment.domain.Comment;
import com.knu.cse.comment.service.CommentService;
import com.knu.cse.deletemember.domain.DeleteMember;
import com.knu.cse.deletemember.repository.DeleteMemberRepository;
import com.knu.cse.email.service.AuthService;
import com.knu.cse.email.util.CookieUtil;
import com.knu.cse.email.util.JwtUtil;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.image.S3UploadService;
import com.knu.cse.member.dto.ChangePasswordForm;
import com.knu.cse.member.dto.DeleteForm;
import com.knu.cse.member.dto.LoginSuccessDto;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.repository.MemberRepository;
import java.io.IOException;

import com.knu.cse.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final DeleteMemberRepository deleteMemberRepository;

    private final CookieUtil cookieUtil;

    @Transactional
    public void updateProfileImageAndNickName(MultipartFile image,String newNickName,Long userId) throws Exception {
        Member member = memberRepository.findById(userId).orElseThrow(()->
            new NotFoundException("존재하지 않는 회원입니다."));


       if(newNickName!=null && !changeNickName(newNickName,member)){
           throw new IllegalStateException("닉네임이 중복되었습니다. 다른 닉네임을 사용해주세요");
       }

       if(image!=null) {
           changeProfileImage(image,member);
       }
    }

    @Transactional
    public boolean changeNickName(String newNickName,Member member){
        if(authService.isDuplicateNickName(newNickName)){
            return false;
        }

        member.changeNickName(newNickName);
        return true;
    }

    @Transactional
    public boolean changeProfileImage(MultipartFile file,Member member) throws IOException {
        String imagePath = uploadService.upload(file, "static");
        member.changeProfileImage(imagePath);
        return true;
    }

    @Transactional
    public void changePassword(ChangePasswordForm changePasswordForm, Long userId) throws NotFoundException{
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다."));
        authService.comparePassword(changePasswordForm.getCurrentPassword(),member.getPassword());
        member.changePassword(passwordEncoder.encode(changePasswordForm.getChangePassword()));
    }

    @Transactional
    public void deleteMember(Long userId, DeleteForm deleteForm, HttpServletResponse res){
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다."));
        authService.comparePassword(deleteForm.getPassword(), member.getPassword());

        if (member.getReservation() != null){
            throw new IllegalStateException("예약된 좌석이 있습니다. 좌석 반납 후 다시 진행해주세요.");
        }


        DeleteMember deleteMember = DeleteMember.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .studentId(member.getStudentId())
                .profileImageUrl(member.getProfileImageUrl())
                .gender(member.getGender())
                .major(member.getMajor())
                .role(member.getRole())
                .build();
        deleteMemberRepository.save(deleteMember);
        member.deleteMember();

        //세션 날리기
        Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, null);
        Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, null);
        accessToken.setMaxAge(0);
        refreshToken.setMaxAge(0);
        res.addCookie(accessToken);
        res.addCookie(refreshToken);
    }

}
