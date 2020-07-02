package com.umss.todo.common.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequestDto {
	
	@NotBlank
	@Size(min = 2, max = 20, message = "First Name field can not be less than 2 characters and more than 20.")
	private String firstName;
	@Size(min = 2, max = 50, message = "Last Name field can not be less than 2 characters and more than 50.")
	@NotBlank
	private String lastName;
	
	
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
}
