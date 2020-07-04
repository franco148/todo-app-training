package com.umss.todo.exception;

public class TaskNotFoundException extends Exception {

	private static final String ERROR_MESSAGE = "Task with ID=%s could not be found.";
	
	public TaskNotFoundException(Long taskId) {
		super(String.format(ERROR_MESSAGE, taskId));
	}
}
