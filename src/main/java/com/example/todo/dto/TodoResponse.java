package com.example.todo.dto;

import com.example.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponse {
    private final Long id;
    private final String title;
    private final boolean completed;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.completed = todo.isCompleted();
        this.createdAt = todo.getCreatedAt();
        this.updatedAt = todo.getUpdatedAt();
    }
}
