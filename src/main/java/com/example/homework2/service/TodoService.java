package com.example.homework2.service;

import com.example.homework2.dto.TodoCreateRequest;
import com.example.homework2.dto.TodoResponse;
import com.example.homework2.dto.TodoUpdateRequest;
import com.example.homework2.entity.Todo;
import com.example.homework2.exception.TodoNotFoundException;
import com.example.homework2.repository.TodoRepository;
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
