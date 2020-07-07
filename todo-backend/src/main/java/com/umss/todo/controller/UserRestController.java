package com.umss.todo.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umss.todo.common.dto.request.TaskRequestDto;
import com.umss.todo.common.dto.request.UserCredentialsDto;
import com.umss.todo.common.dto.request.UserRequestDto;
import com.umss.todo.common.dto.response.TaskResponseDto;
import com.umss.todo.common.dto.response.UserResponseDto;
import com.umss.todo.exception.ExceptionResponse;
import com.umss.todo.exception.TaskValidationException;
import com.umss.todo.exception.UserNotFoundException;
import com.umss.todo.service.TaskService;
import com.umss.todo.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserRestController {
	

	private UserService userService;
	private TaskService taskService;
	
	
	@Autowired
	public UserRestController(UserService userService,
							  TaskService taskService) {
		this.userService = userService;
		this.taskService = taskService;
	}
	
	
	// http://localhost:8080/users
	@GetMapping
	public List<UserResponseDto> getAllSystemUsers() {
		return userService.getAllUsers();
	}
	
	// http://localhost:8080/users
	@PostMapping
	public UserResponseDto registerUser(@Valid @RequestBody UserCredentialsDto credentials) {		
		return userService.registerUser(credentials);
	}
	
	// http://localhost:8080/users/{userId}
	@GetMapping("/{userId}")
	public ResponseEntity<?> findById(@PathVariable("userId") Long userId) {
		try {
			UserResponseDto responseDto = userService.findById(userId);
			return ResponseEntity.ok(responseDto);
		} catch (UserNotFoundException e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
		} catch (Exception e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
	}
	
	// http://localhost:8080/users/{userId}
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUserProfile(@PathVariable("userId") Long userId,
											 @Valid @RequestBody UserRequestDto userProfile) {
		try {
			UserResponseDto updatedDto = userService.update(userId, userProfile);
			return ResponseEntity.ok(updatedDto);
		} catch (UserNotFoundException e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
		} catch (Exception e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
	}
	
	// http://localhost:8080/users/{userId}/task
	@PostMapping("{userId}/task")
	public ResponseEntity<?> addTask(@PathVariable("userId") Long userId,
								   @Valid @RequestBody TaskRequestDto taskDto) {
		try {
			TaskResponseDto responseDto = taskService.addTask(userId, taskDto);
			return ResponseEntity.ok(responseDto);
		} catch (TaskValidationException e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
		} catch (UserNotFoundException e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
		} catch (Exception e) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), new Date());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
	}
	
	// http://localhost:8080/users/{userId}/task/filteredby?startDate={date}&endDate={date}&priority={priority}&state={state}
	@GetMapping("/{userId}/task/filteredby")
	public Set<TaskResponseDto> getUserTasksFilteredBy(
			@PathVariable("userId") Long userId,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate endDate,
			@RequestParam(name = "priority", required = false) @Pattern(regexp = "HIGH|MEDIUM|LOW|NORMAL", flags = Flag.CANON_EQ) String priority,
			@RequestParam(name = "state", required = false) @Pattern(regexp = "PLANNED|PAUSED|IN_PROGRESS|CANCELLED|COMPLETED", flags = Flag.CANON_EQ) String state) {
		return null;
	}
	
	// http://localhost:8080/users/{userId}/task/groupedby/{groupBy}?startDate={date}&endDate={date}
	@GetMapping("/{userId}/task/groupedby/{groupBy}")
	public Map<String, Set<TaskResponseDto>> getUserTasksGroupedBy(
			@PathVariable("userId") Long userId,
			@PathVariable("groupBy") @Pattern(regexp = "STATE|PRIORITY", flags = Flag.CANON_EQ) String groupBy,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {

		try {
			validateStartAndDate(startDate, endDate);
		} catch (Exception e) {
//			throw new Algo();
		}
		
//		taskService.getUserTasksGroupedBy(userId, from, to, groupBy);
		
		return null;
	}
	
	
	private void validateStartAndDate(LocalDate startDate, LocalDate endDate) {
		// When both are null, nothing to validate
		if (null == startDate && null == endDate) {
			return;
		}
		
		// If startDate is not null, then endDate should not be null.
		if (null != startDate && null == endDate) {
			throw new IllegalArgumentException("If StartDate is not null, then EndDate should not be either");
		}
		
		// If endDate is not null, then startDate should not be null.
		if (null != endDate && null == startDate) {
			throw new IllegalArgumentException("EndDate is not null, then StartDate should not be either");
		}
		
		// When StartDate is not null and EndDate is not either, StartDate should not be after EndDate
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("StartDate should not be greater than EndDate");
		}
	}
}
