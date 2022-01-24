package com.exetel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.exetel.entity.Todo;
import com.exetel.enums.TodoStatus;
import com.exetel.model.TodoDto;
import com.exetel.repository.TodoRepository;
import com.exetel.service.impl.TodoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TodoServiceImplTest {

	@InjectMocks
	private TodoServiceImpl todoServiceImpl;
	
	@Mock
	private TodoRepository todoRepository;
	
	@Spy
	private ModelMapper modelMapper;
	
	@Before(value = "")
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void findTodosByStatusTest() {
	
		Todo todo1 = Todo.builder().name("todo1").description("this is first todo").dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1)
				.build();
		Todo todo2 = Todo.builder().name("todo2").description("this is second todo").dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(2)
				.build();
		
		List<Todo> todos = Stream.of(todo1, todo2).collect(Collectors.toList());
		
		when(todoRepository.findAllByUserIdAndTodoStatus("user1", TodoStatus.Pending)).
		thenReturn(todos);
		
		List<TodoDto> todoDtosResult = todoServiceImpl.findTodosByStatus("user1", TodoStatus.Pending);
		
		assertEquals(todoDtosResult.size(), 2);
		assertEquals(todoDtosResult.get(0).getName(), "todo1");
		
	}
	
	@Test
	public void getTodoByIdTest() {
	
		Optional<Todo> optionalTodo = Optional.of(Todo.builder().name("todo1").description("new todo").dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1)
				.build());
		
		when(todoRepository.findById(1L)).thenReturn(optionalTodo);
		
		
		TodoDto todoDto = todoServiceImpl.getTodoById(1);
		
		assertEquals(todoDto.getName(), "todo1");
		assertEquals(todoDto.getDescription(), "new todo");
		
	}
	
	@Test
	public void deleteTodoByIdTest() {
		
		doNothing().when(todoRepository).deleteById(ArgumentMatchers.anyLong());
		
		todoServiceImpl.deleteTodo(1L);
	
		verify(todoRepository, times(1)).deleteById(ArgumentMatchers.anyLong());
	}
	
	@Test
	public void updateListOfTodoStatusTest() {
	
		Todo todo1 = getTodo("todo 1", "this is first todo", LocalDate.now().plusDays(5), "user1", TodoStatus.Pending);
		Todo todo2 = getTodo("todo 2", "this is second todo", LocalDate.now().plusDays(7), "user1", TodoStatus.Pending);
		
		List<Todo> todos = Stream.of(todo1, todo2).collect(Collectors.toList());
		
		when(todoRepository.findAllById(ArgumentMatchers.anyList())).thenReturn(todos);
		
		todoServiceImpl.updateTodoStatusToDone(Stream.of(1L, 2L).collect(Collectors.toList()));
		
		assertEquals(todo1.getTodoStatus(), TodoStatus.Done);
		assertEquals(todo2.getTodoStatus(), TodoStatus.Done);
		
	}
	
	@Test
	public void updateTodoStatusTest() {
	
		Optional<Todo> optionalTodo = Optional.of(Todo.builder().name("todo1").description("new todo").dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1)
				.build());
		
		when(todoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(optionalTodo);
		
		todoServiceImpl.updateTodoStatusToDone(1L);
		
		assertEquals(optionalTodo.get().getTodoStatus(), TodoStatus.Done);
		
	}
	
	@Test
	public void updateTodoTest() {
	
		Optional<Todo> optionalTodo = Optional.of(Todo.builder().name("todo1").description("new todo").dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1)
				.build());
		
		Todo todo = Todo.builder().name("todo1").description("todo desc").dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1)
				.build();
		
		TodoDto todoDto = TodoDto.builder().name("todo1").description("todo desc").dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1)
		.build();
		
		when(todoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(optionalTodo);
		
		when(todoRepository.save(ArgumentMatchers.any(Todo.class))).thenReturn(todo);
		
		todoServiceImpl.updateTodo(todoDto);
		
		verify(todoRepository, times(1)).save(ArgumentMatchers.any(Todo.class));
		
	}
	
	@Test
	public void createTodoTest() {
		
		Todo todo = Todo.builder().name("todo1").description("new todo").dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1)
				.build();
		
		TodoDto todoDto = TodoDto.builder().name("todo1").description("todo desc").dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1)
		.build();
		
		when(todoRepository.save(ArgumentMatchers.any(Todo.class))).thenReturn(todo);
		
		todoServiceImpl.createTodo(todoDto);
		
		verify(todoRepository, times(1)).save(ArgumentMatchers.any(Todo.class));
		
	}
	
	private Todo getTodo(String name, String description, LocalDate dueDate, String userId, TodoStatus todoStatus) {
		return Todo.builder().name(name).description(description).dueDate(dueDate).userId(userId).todoStatus(todoStatus)
				.build();
	}
	
}
