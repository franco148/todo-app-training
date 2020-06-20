package com.umss.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umss.todo.controller.dto.UserCredentialsDto;
import com.umss.todo.controller.dto.UserResponseDto;
import com.umss.todo.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserRestController {
	
	// FEATURE: backend-GetAllUsersOfTheSystem	
	private UserService userService;
	
	
	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	
	// http://localhost:8080/users
	@GetMapping
	public List<UserResponseDto> getAllSystemUsers() {
		return userService.getAllUsers();
	}
	
	// http://localhost:8080/users
	@PostMapping
	public UserResponseDto registerUser(UserCredentialsDto credentials) {
		
		return null;
	}
	
	//PUT
	
	//DELETE
}
