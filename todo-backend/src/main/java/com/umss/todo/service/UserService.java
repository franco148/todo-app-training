package com.umss.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.umss.todo.controller.dto.UserResponseDto;
import com.umss.todo.reposiroty.SqlConnector;
import com.umss.todo.reposiroty.model.User;

@Service
public class UserService {

	private SqlConnector sqlConnector;
	
	
	@Autowired
	public UserService(@Qualifier("mysql") SqlConnector sqlConnector) {
		this.sqlConnector = sqlConnector;
	}
	
//	@Autowired
//	public UserService(SqlConnector sqlConnector) {
////		this.sqlConnector = sqlConnector;
//	}
	
	
	public List<UserResponseDto> getAllUsers() {
		List<User> userCollectionResponse = sqlConnector.getAllUsers();
		List<UserResponseDto> result = new ArrayList<UserResponseDto>();
		
		for (User user : userCollectionResponse) {
			UserResponseDto dto = new UserResponseDto();
			dto.setId(user.getId());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setEmail(user.getAccount());
			
			result.add(dto);
;		}
		
		return result;
	}
}
