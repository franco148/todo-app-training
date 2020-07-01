package com.umss.todo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umss.todo.controller.dto.TaskRequestDto;
import com.umss.todo.controller.dto.TaskResponseDto;
import com.umss.todo.controller.dto.UserCredentialsDto;
import com.umss.todo.controller.dto.UserRequestDto;
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
	public UserResponseDto registerUser(@Valid @RequestBody UserCredentialsDto credentials) {		
		return userService.registerUser(credentials);
	}
	
	// http://localhost:8080/users/{userId}
	@PutMapping("/{userId}")
	public UserResponseDto updateUserProfile(@PathVariable("userId") Long userId,
											 @Valid @RequestBody UserRequestDto userProfile) {
		try {
			return userService.update(userId, userProfile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// http://localhost:8080/users/{userId}/task
	@PostMapping("{userId}/task")
	public TaskResponseDto addTask(@PathVariable("userId") Long userId,
								   @Valid @RequestBody TaskRequestDto taskDto) {
		try {
			return userService.addTask(userId, taskDto);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
