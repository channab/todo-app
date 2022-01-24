package com.exetel.service;

import java.util.List;

import com.exetel.enums.TodoStatus;
import com.exetel.model.TodoDto;

public interface TodoService {

	List<TodoDto> findTodosByStatus(String userId, TodoStatus todoStatus);

	void createTodo(TodoDto tododto);

	TodoDto getTodoById(long id);

	void updateTodo(TodoDto tododto);

	void updateTodoStatusToDone(List<Long> ids);

	void updateTodoStatusToDone(Long id);

	void deleteTodo(Long id);
}
