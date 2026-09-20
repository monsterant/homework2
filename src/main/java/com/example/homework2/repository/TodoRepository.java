package com.example.homework2.repository;

import com.example.homework2.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo, Long> {
}