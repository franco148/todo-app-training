package com.umss.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umss.todo.controller.dto.TaskRequestDto;
import com.umss.todo.controller.dto.TaskResponseDto;
import com.umss.todo.controller.dto.UserCredentialsDto;
import com.umss.todo.controller.dto.UserRequestDto;
import com.umss.todo.controller.dto.UserResponseDto;
import com.umss.todo.reposiroty.TaskRepository;
import com.umss.todo.reposiroty.UserRepository;
import com.umss.todo.reposiroty.model.Task;
import com.umss.todo.reposiroty.model.User;

@Service
public class UserService {

	private UserRepository userRepository;	
	private ModelMapper modelMapper;
	
	
	@Autowired
	public UserService(UserRepository userRepository,
					   ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	
	
	public List<UserResponseDto> getAllUsers() {
		
		List<UserResponseDto> userModelCollection = this.userRepository
							 .findAll()
							 .stream()
							 .map(user -> {
								 UserResponseDto response = modelMapper.map(user, UserResponseDto.class);
                                 return response;												 
							 }).collect(Collectors.toList());
		
		
		return userModelCollection;
	}
	
	public UserResponseDto registerUser(UserCredentialsDto credentials) {
		User userToPersist = modelMapper.map(credentials, User.class);
		
		User persistedUser = userRepository.save(userToPersist);
		
		return modelMapper.map(persistedUser, UserResponseDto.class);
	}
	
	public UserResponseDto update(Long userId, UserRequestDto userRequestDto) throws Exception {
		if (!userRepository.existsById(userId)) {
			throw new Exception("User with ID=" + userId + " does not exist.");
		}
		
		User foundUser = userRepository.getOne(userId);
		modelMapper.map(userRequestDto, foundUser);
		
		User updatedUser = userRepository.save(foundUser);
		UserResponseDto updatedDto = modelMapper.map(updatedUser, UserResponseDto.class);
		
		return updatedDto;
	}
	
	
}
