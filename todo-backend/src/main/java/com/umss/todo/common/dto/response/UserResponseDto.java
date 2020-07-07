package com.umss.todo.common.dto.response;

import java.util.HashSet;
import java.util.Set;

public class UserResponseDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String nickName;
	private String email;
	private Set<TaskResponseDto> tasks = new HashSet<TaskResponseDto>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<TaskResponseDto> getTasks() {
		return tasks;
	}
	public void setTasks(Set<TaskResponseDto> tasks) {
		this.tasks = tasks;
	}	
}
