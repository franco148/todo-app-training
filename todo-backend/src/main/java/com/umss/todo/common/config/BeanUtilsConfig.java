package com.umss.todo.common.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.umss.todo.common.dto.request.UserRequestDto;
import com.umss.todo.common.dto.response.UserResponseDto;
import com.umss.todo.persistence.domain.User;

@Configuration
public class BeanUtilsConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
		// Configuring mapping when UserDTO to Entity
		modelMapper.addMappings(new PropertyMap<UserRequestDto, User>() {
			@Override
			protected void configure() {
				skip(destination.getPassword());
			}
		});
		
		// Configuring mapping when Entity to UserDTO
		modelMapper.addMappings(new PropertyMap<User, UserResponseDto>() {
			@Override
			protected void configure() {
				// skip(destination.getPassword());
				skip(destination.getTasks());				
			}
		});
		
		return modelMapper;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
