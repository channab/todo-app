package com.exetel.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import org.springframework.stereotype.Service;

import com.exetel.entity.Todo;
import com.exetel.enums.TodoStatus;
import com.exetel.model.TodoDto;
import com.exetel.repository.TodoRepository;
import com.exetel.service.TodoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

	
	private final TodoRepository todoRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public List<TodoDto> findTodosByStatus(String userId, TodoStatus todoStatus) {
		
		List<Todo> todos = todoRepository.findAllByUserIdAndTodoStatus(userId, todoStatus);
		
		Type listType = new TypeToken<List<TodoDto>>(){}.getType();
		List<TodoDto> todoDtos = modelMapper.map(todos,listType);

		
		return todoDtos;
	}

	@Override
	public void createTodo(TodoDto tododto) {
		
		Todo todo = modelMapper.map(tododto, Todo.class);
		todo.setTodoStatus(TodoStatus.Pending);
		
		todoRepository.save(todo);
	}

	@Override
	public TodoDto getTodoById(long id) {
		
		Optional<Todo> todo = todoRepository.findById(id);
		
		if(todo.isPresent()) {
			TodoDto todoDto = modelMapper.map(todo.get(), TodoDto.class);
			
			return todoDto;
		}
		
		throw new NoSuchElementException(String.format("Todo item %d not found", id));
	}

	@Override
	public void updateTodo(TodoDto tododto) {
		
		Optional<Todo> optionalTodo = todoRepository.findById(tododto.getId());
		
		if(optionalTodo.isPresent()) {
			Todo todo = optionalTodo.get();
			todo.setName(tododto.getName());
			todo.setDescription(tododto.getDescription());
			todo.setDueDate(tododto.getDueDate());
			
			todoRepository.save(todo);
		}
		else {
			throw new NoSuchElementException(String.format("Todo item %d not found", tododto.getId()));
		}
		
		
	}

	@Override
	public void updateTodoStatusToDone(List<Long> ids) {
		
		List<Todo> todos = todoRepository.findAllById(ids);
		
		todos.stream().forEach(todo -> 
		{
			todo.setTodoStatus(TodoStatus.Done);
		});

	}

	@Override
	public void updateTodoStatusToDone(Long id) {
		
		Todo todo = todoRepository.findById(id).get();
		
		todo.setTodoStatus(TodoStatus.Done);

	}

	@Override
	public void deleteTodo(Long id) {
		
		todoRepository.deleteById(id);
	}

}
