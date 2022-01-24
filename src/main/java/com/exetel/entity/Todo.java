package com.exetel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.exetel.enums.TodoStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "todo", indexes = { @Index(name = "idx_todo_user_id", columnList = "userId", unique = false) })
public class Todo {

	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private String name;
	@Column(length = 500)
	private String description;
	private LocalDate dueDate;
	@CreationTimestamp
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	@Enumerated(EnumType.STRING)
	private TodoStatus todoStatus;
	@Column(nullable = false)
	private String userId;
}
