package com.knu.cse.board.dto;

import com.knu.cse.board.domain.Category;
import lombok.Getter;

@Getter
public class BoardForm {

    private Category category;

    private String title;

    private String content;


    public BoardForm(){}
}
