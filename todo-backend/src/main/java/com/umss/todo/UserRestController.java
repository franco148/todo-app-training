package com.umss.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {
	
	// FEATURE: backend-GetAllUsersOfTheSystem	
	private SqlConnector sqlConnector;
	
	
	@Autowired
	public UserRestController(@Qualifier("mysql") SqlConnector sqlConnector) {
		this.sqlConnector = sqlConnector;
	}
	
	
	// http://localhost:8080/users
	@GetMapping
	public List<User> getAllSystemUsers() {
		return sqlConnector.getAllUsers();
	}
}
