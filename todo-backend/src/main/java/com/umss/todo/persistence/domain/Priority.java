package com.umss.todo.persistence.domain;

public enum Priority {

	HIGH,
	MEDIUM,
	LOW,
	NORMAL;
	
	public static Priority tryParse(String priorityText) {
		for (Priority priority : Priority.values()) {
			if (priority.name().equalsIgnoreCase(priorityText)) {
				return priority;
			}
		}
		
		return null;
	}
}
