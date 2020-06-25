package com.umss.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.umss.todo.controller.dto.UserCredentialsDto;
import com.umss.todo.controller.dto.UserResponseDto;
import com.umss.todo.reposiroty.UserRepository;
import com.umss.todo.reposiroty.model.User;

@Service
public class UserService {

	private UserRepository userRepository;
	
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
//	@Autowired
//	public UserService(SqlConnector sqlConnector) {
////		this.sqlConnector = sqlConnector;
//	}
	
	
	public List<UserResponseDto> getAllUsers() {		
		List<UserResponseDto> userCollectionResponse = new ArrayList<UserResponseDto>();
		
		List<User> userModelCollection = this.userRepository.findAll();
		
		
		
		for (User user : userModelCollection) {
			UserResponseDto userDto = new UserResponseDto();
			userDto.setId(user.getId());
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setEmail(user.getEmail());
			
			userCollectionResponse.add(userDto);
		}
		
		return userCollectionResponse;
	}
	
	public UserCredentialsDto registerUser(UserCredentialsDto credentials) {
		return null;
	}
}
