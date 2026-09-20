package com.example.homework2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String authorNickname;
}