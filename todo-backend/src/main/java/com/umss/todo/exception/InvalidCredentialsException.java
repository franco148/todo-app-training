package com.umss.todo.exception;

public class InvalidCredentialsException extends Exception {

	private static final String ERROR_MESSAGE = "Invalid email or password, the user was unable to login into the system.";
	
	public InvalidCredentialsException() {
		super(ERROR_MESSAGE);
	}
}
