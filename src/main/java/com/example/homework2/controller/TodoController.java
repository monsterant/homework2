package com.example.homework2.controller;

import com.example.homework2.dto.TodoCreateRequest;
import com.example.homework2.dto.TodoResponse;
import com.example.homework2.dto.TodoUpdateRequest;
import com.example.homework2.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService  todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody TodoCreateRequest request) {
        todoService.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoResponse findOne(@PathVariable Long id) {
        return todoService.findOne(id);
    }

    @PatchMapping("/{id}")
    public TodoResponse update(@PathVariable Long id,@Valid @RequestBody TodoUpdateRequest request) {
        return todoService.update(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        todoService.delete(id);
    }

}