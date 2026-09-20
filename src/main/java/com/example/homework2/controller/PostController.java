package com.example.homework2.controller;

import com.example.homework2.dto.PostCreateRequest;
import com.example.homework2.dto.PostDetailResponse;
import com.example.homework2.dto.PostListResponse;
import com.example.homework2.dto.PostUpdateRequest;
import com.example.homework2.entity.Post;
import com.example.homework2.repository.MemberRepository;
import com.example.homework2.repository.PostRepository;
import com.example.homework2.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(postId, authentication.getName());
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long postId, @Valid @RequestBody PostUpdateRequest request, Authentication authentication
    ) {
        postService.update(postId, request, authentication.getName()
        );
    }

    @GetMapping
    public Page<PostListResponse> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (size != 10 && size != 20 && size != 30) {
            throw new IllegalArgumentException("size는 10, 20, 30만 가능합니다.");
        }

        return postService.findAll(page, size);
    }


}
