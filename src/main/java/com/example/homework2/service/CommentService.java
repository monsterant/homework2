package com.example.homework2.service;

import com.example.homework2.dto.CommentCreateRequest;
import com.example.homework2.dto.CommentUpdateRequest;
import com.example.homework2.entity.Comment;
import com.example.homework2.entity.Post;
import com.example.homework2.exception.CommentNotFoundException;
import com.example.homework2.exception.ForbiddenException;
import com.example.homework2.exception.MemberNotFoundException;
import com.example.homework2.exception.PostNotFoundException;
import com.example.homework2.repository.CommentRepository;
import com.example.homework2.repository.MemberRepository;
import com.example.homework2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.homework2.entity.Member;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void create(Long postId, CommentCreateRequest request,String loginEmail){
        Member member = memberRepository.findByEmail(loginEmail).orElseThrow(() -> new MemberNotFoundException());
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));


        Comment parent = null;

        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow();

            // 대댓글의 대댓글은 금지
            if (parent.getParent() != null) {
                throw new IllegalArgumentException("대댓글에는 답글을 작성할 수 없습니다.");
            }

            // 다른 게시글의 댓글을 부모로 지정하는 것도 금지
            if (!parent.getPost().getId().equals(postId)) {
                throw new IllegalArgumentException("해당 게시글의 댓글이 아닙니다.");
            }
        }

        Comment comment = new Comment(
                request.getContent(),
                member,
                post,
                parent
        );

        commentRepository.save(comment);

    }
    @Transactional
    public void update(Long id, CommentUpdateRequest request, String loginEmail) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));

        if (!comment.getAuthor().getEmail().equals(loginEmail)) {
            throw new ForbiddenException("본인의 댓글만 수정할 수 있습니다.");
        }

        if (comment.isDeleted()) {
            throw new IllegalArgumentException("삭제된 댓글은 수정할 수 없습니다.");
        }

        comment.update(request.getContent());
    }

    @Transactional
    public void delete(Long commentId, String loginEmail) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getAuthor().getEmail().equals(loginEmail)) {
            throw new ForbiddenException("본인의 댓글만 삭제할 수 있습니다.");
        }

        comment.markDeleted();
    }

}
