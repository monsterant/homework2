package com.example.homework2.service;


import com.example.homework2.dto.PostCreateRequest;
import com.example.homework2.dto.PostDetailResponse;
import com.example.homework2.dto.PostListResponse;
import com.example.homework2.dto.PostUpdateRequest;
import com.example.homework2.entity.Member;
import com.example.homework2.entity.Post;
import com.example.homework2.exception.ForbiddenException;
import com.example.homework2.exception.PostNotFoundException;
import com.example.homework2.repository.CommentRepository;
import com.example.homework2.repository.MemberRepository;
import com.example.homework2.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public void create(PostCreateRequest request,String loginEmail) {
        Member member = memberRepository.findByEmail(loginEmail).orElseThrow();
        Post post = new Post(request.getTitle(),request.getContent(),member);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findById(long id) {
        Post post = postRepository.findById(id).orElseThrow();

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getNickname()
        );
    }

    public void delete(long id,String loginEmail) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        if (!post.getAuthor().getEmail().equals(loginEmail)) {
            throw new ForbiddenException("본인의 게시글만 삭제할 수 있습니다.");
        }

        post.markDeleted();
    }

    public void update(Long id,PostUpdateRequest request,String loginEmail){
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        if (!post.getAuthor().getEmail().equals(loginEmail)) {
            throw new ForbiddenException("본인의 게시글만 수정할 수 있습니다.");
        }

        post.update(request.getTitle(), request.getContent());
    }
    @Transactional(readOnly = true)
    public Page<PostListResponse> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));


        Page<Post> posts = postRepository.findByDeletedFalse(pageable);

        List<Long> postIds = posts.getContent()
                .stream()
                .map(Post::getId)
                .toList();
        List<Object[]> countResults = commentRepository.countByPostIds(postIds);
        Map<Long, Long> commentCountMap = countResults.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        return posts.map(post ->
                new PostListResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getAuthor().getNickname(),
                        commentCountMap.getOrDefault(post.getId(), 0L),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                )
        );
    }


}
