package com.example.homework2.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostUpdateRequest {
    @Size(max = 30, message = "제목은 30자 이하로 입력해주세요.")
    private String title;

    private String content;
}
