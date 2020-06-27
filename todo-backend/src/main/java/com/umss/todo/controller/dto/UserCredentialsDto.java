package com.umss.todo.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserCredentialsDto {
	
	@NotBlank(message = "Email field should not be null or empty.")
	@Email(message = "Email field should be a valid email address.")
//	@MyEmailValidator(message = "")
	private String email;
	@Size
	(
		min = 6, 
		max = 30, 
		message = "Password field should contain at least 6 characters and at most 30."
	)
	private String password;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	
}
