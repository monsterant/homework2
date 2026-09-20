package com.example.homework2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostListResponse {
    private Long id;
    private String title;
    private String authorNickname;
    private long commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
