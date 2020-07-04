package com.umss.todo.exception;

import java.util.Date;

public class ExceptionResponse {

	private String message;
	private Date timestamp;
	
	
	
	public ExceptionResponse(String message, Date timestamp) {
		super();
		this.message = message;
		this.timestamp = timestamp;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
