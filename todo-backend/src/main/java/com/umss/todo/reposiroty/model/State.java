package com.umss.todo.reposiroty.model;

public enum State {
	
	PLANNED,
	PAUSED,
	IN_PROGRESS,
	CANCELLED,
	COMPLETED;
	
	public static State tryParse(String stateText) {
		for (State state : State.values()) {
			if (state.name().equalsIgnoreCase(stateText)) {
				return state;
			}
		}
		
		return null;
	}
}
