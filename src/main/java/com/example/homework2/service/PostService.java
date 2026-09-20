package com.example.homework2.service;


import com.example.homework2.dto.PostCreateRequest;
import com.example.homework2.dto.PostDetailResponse;
import com.example.homework2.entity.Member;
import com.example.homework2.entity.Post;
import com.example.homework2.repository.MemberRepository;
import com.example.homework2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

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
}
