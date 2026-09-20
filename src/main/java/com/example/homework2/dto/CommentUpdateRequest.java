package com.example.homework2.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
@Getter
public class CommentUpdateRequest {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}