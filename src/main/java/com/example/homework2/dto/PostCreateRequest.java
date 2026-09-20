package com.example.homework2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 30, message = "제목은 30자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}