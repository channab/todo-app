package com.exetel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.exetel.enums.TodoStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {

	private long id;
	@NotEmpty
	private String name;
	private String description;
	@FutureOrPresent
	private LocalDate dueDate;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private TodoStatus todoStatus;
	private String userId;
}
