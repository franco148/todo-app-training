package com.umss.todo.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {

	private static final String ERROR_MESSAGE = "User with ID=%s could not be found in the system.";
	
	public UserNotFoundException(Long userId) {
		super(String.format(ERROR_MESSAGE, userId));
	}
}
