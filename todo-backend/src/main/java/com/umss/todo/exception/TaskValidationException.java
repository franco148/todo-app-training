package com.umss.todo.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.umss.todo.persistence.domain.Priority;
import com.umss.todo.persistence.domain.State;

public class TaskValidationException extends Exception {

	public static final String START_END_DATETIME_FIELD = "DATETIME";
	public static final String PRIORITY_FIELD = "PRIORITY";
	public static final String STATE_FIELD = "STATE";
	
	private static final String PREFIX_TASK_ERROR_MESSAGE = "Process can not continue due to the following errors: ";
	private static final String PREFIX_FIELD_ERROR_MESSAGE = "Invalid value entered, allowed values are: ";
	private static final Map<String, String> TASK_ERROR_MESSAGES_MAP = new HashMap<>() {
		{
			put(START_END_DATETIME_FIELD, "EndTime should be greather than StartTime");
			put(PRIORITY_FIELD, getPriorityErrorMessage());
			put(STATE_FIELD, getStateErrorMessage());
		}
	};
	
	
	public TaskValidationException(Set<String> errorMessagesKeys) {
		super(PREFIX_TASK_ERROR_MESSAGE + buildTaskValidationErrorMessage(errorMessagesKeys).toString());
	}
	
	
	
	private static Map<String, String> buildTaskValidationErrorMessage(Set<String> errorMessagesKeys) {
		Map<String, String> errorMessageResponse = new HashMap<String, String>();
		
		errorMessagesKeys.forEach(errorKey -> {
			if (TASK_ERROR_MESSAGES_MAP.containsKey(errorKey)) {
				errorMessageResponse.put(errorKey, TASK_ERROR_MESSAGES_MAP.get(errorKey));
			}
		});
		
		return errorMessageResponse;
	}
	
	private static String getPriorityErrorMessage() {
		Set<String> validPriorityValues = Arrays.asList(Priority.values()).stream()
										  .map(priority -> priority.toString())
										  .collect(Collectors.toSet());
		
		return PREFIX_FIELD_ERROR_MESSAGE + validPriorityValues;
	}
	
	private static String getStateErrorMessage() {
		Set<String> validStateValues = Arrays.asList(State.values()).stream()
									  .map(state -> state.toString())
									  .collect(Collectors.toSet());

		return PREFIX_FIELD_ERROR_MESSAGE + validStateValues;
	}
	
	
	/**
	 * Process can not continue due to the following errors: 
	 * {
	 * 	DATETIME=EndTime should be greather than StartTime, 
	 * 	STATE=PAUSEDDDDD Is not a correct value, allowed values are: [IN_PROGRESS, PAUSED, CANCELLED, COMPLETED, PLANNED], 
	 * 	PRIORITY=MEDIUMMMM Is not a correct value, allowed values are: [HIGH, MEDIUM, LOW, NORMAL]
	 * }
	 */
}
