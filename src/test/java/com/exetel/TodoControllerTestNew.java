package com.exetel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.exetel.controller.ToDoController;
import com.exetel.enums.TodoStatus;
import com.exetel.model.TodoDto;
import com.exetel.service.TodoService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ToDoController.class)
@Import(TodoControllerTestNew.TestConfig.class)
public class TodoControllerTestNew {

	@MockBean
	private TodoService todoService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testMethod() throws Exception {

		TodoDto todoDto1 = TodoDto.builder().name("todo1").description("todo first")
				.dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1).build();
		TodoDto todoDto2 = TodoDto.builder().name("todo2").description("todo second")
				.dueDate(LocalDate.now().plusDays(5)).userId("user1").todoStatus(TodoStatus.Pending).id(1).build();

		List<TodoDto> todos = Stream.of(todoDto1, todoDto2).collect(Collectors.toList());

		when(todoService.findTodosByStatus(ArgumentMatchers.anyString(), ArgumentMatchers.any(TodoStatus.class)))
				.thenReturn(todos);

		mockMvc.perform(get("/to-dos")).andExpect(status().isOk());
	}

	@TestConfiguration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@Import({ ToDoController.class })
	public static class TestConfig {
		@Bean
		public GrantedAuthoritiesMapper authoritiesMapper() {
			return new NullAuthoritiesMapper();
		}
	}

	private void configureSecurityContext(String username, String... roles) {
		final Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn(username);

		final OidcKeycloakAccount account = mock(OidcKeycloakAccount.class);
		when(account.getRoles()).thenReturn(new HashSet<>(Arrays.asList(roles)));
		when(account.getPrincipal()).thenReturn(principal);

		final KeycloakAuthenticationToken authentication = mock(KeycloakAuthenticationToken.class);
		when(authentication.getAccount()).thenReturn(account);

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
