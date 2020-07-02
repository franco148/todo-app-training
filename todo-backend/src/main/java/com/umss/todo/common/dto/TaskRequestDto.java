package com.umss.todo.common.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TaskRequestDto {
	
	@NotBlank(message = "Title field should not be empty or null.")
	@Size(min = 5, max = 50, message = "Title mush have at least 5 characters and at most 50.")
	private String title;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;	
	private String state;
	private String priority;
	
	@JsonIgnore
	private LocalDateTime createdAt;
	
	
	public TaskRequestDto() {
		state = "PLANNED";
		priority = "NORMAL";
		createdAt = LocalDateTime.now();
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}


	public LocalDateTime getEndTime() {
		return endTime;
	}


	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
