package com.exetel.controller;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exetel.enums.TodoStatus;
import com.exetel.model.TodoDto;
import com.exetel.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/to-dos")
@CrossOrigin
public class ToDoController {

	private final TodoService todoService;

	@RolesAllowed("app_user")
	@GetMapping
	public List<TodoDto> getToDos(@RequestParam(value = "status") TodoStatus todoStatus, Principal principal) {

		return todoService.findTodosByStatus(getKeycloakUserId(principal), todoStatus);
	}

	@RolesAllowed("app_user")
	@PostMapping
	public void createTodo(@Valid @RequestBody TodoDto todoDto, Principal principal) {
		todoDto.setUserId(getKeycloakUserId(principal));
		todoService.createTodo(todoDto);
	}

	@RolesAllowed("app_user")
	@GetMapping("/{id}")
	public TodoDto getToDo(@PathVariable long id, Principal principal) {

		return todoService.getTodoById(id);
	}

	@RolesAllowed("app_user")
	@PutMapping
	public void updateTodo(@Valid @RequestBody TodoDto todoDto, Principal principal) {
		todoService.updateTodo(todoDto);
	}

	@RolesAllowed("app_user")
	@PostMapping("/update-statuses")
	public void updateTodosStatuses(@RequestBody List<Long> ids, Principal principal) {
		todoService.updateTodoStatusToDone(ids);
	}

	@RolesAllowed("app_user")
	@PostMapping("/update-status")
	public void updateTodosStatuses(@RequestBody long id, Principal principal) {
		todoService.updateTodoStatusToDone(id);
	}

	@RolesAllowed("app_user")
	@DeleteMapping("/{id}")
	public void deleteTodo(@PathVariable long id, Principal principal) {
		todoService.deleteTodo(id);
	}

	private String getKeycloakUserId(Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();

		return accessToken.getId();
	}
}
