package com.umss.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.umss.todo.User;
import com.umss.todo.reposiroty.SqlConnector;

@Service
public class UserService {

	private SqlConnector sqlConnector;
	
	
	@Autowired
	public UserService(@Qualifier("mysql") SqlConnector sqlConnector) {
		this.sqlConnector = sqlConnector;
	}
	
	public List<User> getAllUsers() {
		return sqlConnector.getAllUsers();
	}
}
