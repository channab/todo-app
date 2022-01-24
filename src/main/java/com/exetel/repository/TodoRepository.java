package com.exetel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exetel.entity.Todo;
import com.exetel.enums.TodoStatus;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

	List<Todo> findAllByUserIdAndTodoStatus(String userId, TodoStatus todoStatus);
}
