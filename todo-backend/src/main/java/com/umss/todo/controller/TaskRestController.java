package com.umss.todo.controller;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umss.todo.common.dto.request.TaskRequestDto;
import com.umss.todo.common.dto.response.TaskResponseDto;
import com.umss.todo.exception.ExceptionResponse;
import com.umss.todo.exception.InvalidPropertyValueException;
import com.umss.todo.exception.TaskNotFoundException;
import com.umss.todo.exception.TaskValidationException;
import com.umss.todo.service.TaskService;

@Validated
@RestController
@RequestMapping("/tasks")
public class TaskRestController {
	
	private TaskService taskService;
	
	
	@Autowired
	public TaskRestController(TaskService taskService) {
		this.taskService = taskService;
	}
	

	// http://localhost:8080/tasks/{taskId}?state={newTaskState}
	@PatchMapping("/{taskId}")
	public ResponseEntity<?> changeTaskState(
			@PathVariable("taskId") Long taskId,
			@RequestParam("state") @Pattern(regexp = "PLANNED|PAUSED|IN_PROGRESS|CANCELLED|COMPLETED", flags = Flag.CANON_EQ) String taskState) {
		try {
			TaskResponseDto responseDto = taskService.updateTaskState(taskId, taskState);
			return ResponseEntity.ok(responseDto);
		} catch (InvalidPropertyValueException e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
		} catch (TaskNotFoundException e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
		} catch (Exception e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
	}
	
	// http://localhost:8080/tasks/{taskId}
	@PutMapping("/{taskId}")
	public ResponseEntity<?> updateTask(@PathVariable("taskId") Long taskId,
									  @Valid @RequestBody TaskRequestDto taskDto) {
		try {
			TaskResponseDto responseDto = taskService.updateTask(taskId, taskDto);
			return ResponseEntity.ok(responseDto);
		} catch (TaskValidationException e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
		} catch (TaskNotFoundException e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
		} catch (Exception e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
	}
}
