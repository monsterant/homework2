package com.example.homework2.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(long id) {
        super("해당 댓글을 찾을 수 없습니다. id=" + id);
    }
}