package com.knu.cse.board.dto;

import com.knu.cse.board.domain.Category;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BoardForm {

    private Category category;

    private String title;

    private String content;

    private List<MultipartFile> file;

    private List<String> deleteUrl;


    public BoardForm(){}
}
