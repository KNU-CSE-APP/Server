package com.knu.cse.board.dto;

import com.knu.cse.board.domain.Category;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BoardForm {

    @NotBlank(message = "카테고리를 설정해주세요")
    private Category category;

    @NotBlank(message = "타이틀을 설정해주세요")
    private String title;

    @NotBlank(message = "게시물 내용을 입력해주세요")
    private String content;

    private List<MultipartFile> file;

    private List<String> deleteUrl;


    public BoardForm(){}
}
