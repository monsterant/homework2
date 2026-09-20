package com.example.todo.exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(long id) {
        super("Todo 번호 "+id+" 를 찾을 수 없습니다.");
    }
}
