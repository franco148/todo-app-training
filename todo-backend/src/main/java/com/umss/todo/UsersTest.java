package com.umss.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersTest {
	
	// FEATURE: backend-GetAllUsersOfTheSystem
	@Autowired
	private SqlConnector oracleUserConnector;
	
	
//	public UsersTest() {
//		sqlConnector = new MySqlUserConnector();
//	}
//	
//	public UsersTest(SqlConnector sqlConnector) {
//		this.sqlConnector = sqlConnector;
//	}
	
	
	// http://localhost:8080/users
	@GetMapping
	public List<User> getAllSystemUsers() {
		return oracleUserConnector.getAllUsers();
	}
}
