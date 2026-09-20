package com.example.todo.service;

import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.dto.TodoUpdateRequest;
import com.example.todo.entity.Todo;
import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public void create(TodoCreateRequest request) {
        Todo todoCreate = new Todo(request.getTitle());
        todoRepository.save(todoCreate);
    }
    public TodoResponse findOne(long id) {

        return new TodoResponse(todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id)));
    }

    public TodoResponse update(long id, TodoUpdateRequest request) {

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        todo.update(request.getTitle(), request.getCompleted());
        return new TodoResponse(todoRepository.save(todo));
    }

    public void delete(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        todoRepository.delete(todo);
    }






}
