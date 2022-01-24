package com.exetel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private String name;
	private String description;
	private LocalDate dueDate;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private TodoStatus todoStatus;
	private String userId;
}
