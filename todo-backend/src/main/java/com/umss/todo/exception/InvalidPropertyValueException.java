package com.umss.todo.exception;

public class InvalidPropertyValueException extends Exception {

	private static final String ERROR_MESSAGE = "Invalid value=%s for property=%s";
	
	public InvalidPropertyValueException(String propertyName, String propertyValue) {
		super(String.format(ERROR_MESSAGE, propertyValue, propertyName));
	}
}
