package com.example.todo.repository;

import com.example.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface TodoRepository extends JpaRepository<Todo, Long> {
}