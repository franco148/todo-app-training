package com.umss.todo.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends UsernameNotFoundException {

	private static final String ERROR_MESSAGE = "Invalid email or password, the user was unable to login into the system.";
	
	public InvalidCredentialsException() {
		super(ERROR_MESSAGE);
	}
}
