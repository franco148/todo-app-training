package com.umss.todo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TodoBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		UsersTest testAllUsers = new UsersTest(new OracleUserConnector());
//		List<User> result = testAllUsers.getAllSystemUsers();
//		System.out.println("Amount of users: " + result.size());
		
		
		// mypassword
		// $2a$10$yfDvtW23dOrK5Q7UtL2P9eBjk8JuZ2.WtmWkZv8et0Cm3eElx8LT2
	}

}
