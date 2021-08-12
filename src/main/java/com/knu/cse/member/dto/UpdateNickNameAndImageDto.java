package com.knu.cse.member.dto;

import lombok.Getter;

@Getter
public class UpdateNickNameAndImageDto {

    private String newNickName;
    private String newProFileImg;

    public UpdateNickNameAndImageDto(){}

    public UpdateNickNameAndImageDto(String newNickName,String newProFileImg){
        this.newNickName=newNickName;
        this.newProFileImg=newProFileImg;
    }
}
