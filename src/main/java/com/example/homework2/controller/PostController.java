package com.example.homework2.controller;

import com.example.homework2.dto.PostCreateRequest;
import com.example.homework2.dto.PostDetailResponse;
import com.example.homework2.entity.Post;
import com.example.homework2.repository.MemberRepository;
import com.example.homework2.repository.PostRepository;
import com.example.homework2.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody PostCreateRequest request, Authentication authentication) {
        postService.create(request, authentication.getName());
    }
    @GetMapping("/detail/{id}")
    public PostDetailResponse findOne(@PathVariable Long id) {
        return postService.findById(id);
    }
}
