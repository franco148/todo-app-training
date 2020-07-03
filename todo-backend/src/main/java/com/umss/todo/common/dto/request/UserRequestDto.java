package com.umss.todo.common.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.umss.todo.common.validation.Email;

public class UserRequestDto {
	
	@NotBlank(message = "First Name field should not be empty or null.")
	@Size(min = 2, max = 20, message = "First Name field can not be less than 2 characters and more than 20.")
	private String firstName;
	@NotBlank(message = "Last Name field should not be empty or null.")
	@Size(min = 2, max = 50, message = "Last Name field can not be less than 2 characters and more than 50.")
	private String lastName;
	@Size(max = 20, message = "Nick Name must have at most 20 characters.")
	private String nickName;
	@NotBlank(message = "Email can not be empty or blank.")
	@Email(types = {"gmail", "hotmail", "umss"}, domains = {"com", "es", "edu.bo"})
	private String email;
	@NotBlank(message = "Password can not be empty or blank.")
	private String password;
	
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}
