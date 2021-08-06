package com.knu.cse.member.service;

import com.knu.cse.email.service.AuthService;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.image.S3UploadService;
import com.knu.cse.member.dto.ChangePasswordForm;
import com.knu.cse.member.model.Member;
import com.knu.cse.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Transactional
    public String updateProfileImage(MultipartFile file, Long userId) throws Exception {
        Member member = memberRepository.findById(userId).orElseThrow(()->
            new NotFoundException("존재하지 않는 회원입니다."));
        String imagePath = uploadService.upload(file, "static");
        member.changeProfileImage(imagePath);
        return imagePath;
    }

    @Transactional
    public void changePassword(ChangePasswordForm changePasswordForm, Long userId) throws NotFoundException{
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다."));
        authService.comparePassword(changePasswordForm.getCurrentPassword(),member.getPassword());
        member.changePassword(passwordEncoder.encode(changePasswordForm.getChangePassword()));
    }


}
