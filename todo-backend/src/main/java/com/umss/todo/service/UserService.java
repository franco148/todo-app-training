package com.umss.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.umss.todo.common.dto.request.UserCredentialsDto;
import com.umss.todo.common.dto.request.UserRequestDto;
import com.umss.todo.common.dto.response.UserResponseDto;
import com.umss.todo.exception.InvalidCredentialsException;
import com.umss.todo.exception.UserNotFoundException;
import com.umss.todo.persistence.domain.User;
import com.umss.todo.persistence.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

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
	
	public UserResponseDto findById(Long userId) throws UserNotFoundException {
		User foundUser = userRepository.findById(userId)
						 .orElseThrow(() -> new UserNotFoundException(userId));
		
		return modelMapper.map(foundUser, UserResponseDto.class);
	}
	
	public UserResponseDto findByEmail(String email) throws InvalidCredentialsException {
		User foundUser = userRepository.findByEmail(email)
						 .orElseThrow(() -> new InvalidCredentialsException());
		
		UserResponseDto foundUserDto = modelMapper.map(foundUser, UserResponseDto.class);
		
		return foundUserDto;
	}
	
	public UserResponseDto registerUser(UserCredentialsDto credentials) {
		// Convert DTO to Entity for persistence
		User userToPersist = modelMapper.map(credentials, User.class);
		
		// Save the Entity
		User persistedUser = userRepository.save(userToPersist);
		
		// Convert the Entity to DTO to return
		return modelMapper.map(persistedUser, UserResponseDto.class);
	}
	
	
	public UserResponseDto update(Long userId, UserRequestDto userRequestDto) throws UserNotFoundException {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException(userId);
		}
		
		User foundUser = userRepository.getOne(userId);
		modelMapper.map(userRequestDto, foundUser);
		
		User updatedUser = userRepository.save(foundUser);
		UserResponseDto updatedDto = modelMapper.map(updatedUser, UserResponseDto.class);
		
		return updatedDto;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
