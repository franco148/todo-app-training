package com.umss.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umss.todo.User;
import com.umss.todo.reposiroty.SqlConnector;
import com.umss.todo.service.UserService;

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
	public List<User> getAllSystemUsers() {
		return userService.getAllUsers();
	}
}