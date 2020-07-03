package com.umss.todo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
	public UserResponseDto findById(@PathVariable("userId") Long userId) {
		try {
			return userService.findById(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// http://localhost:8080/users/{userId}
	@PutMapping("/{userId}")
	public UserResponseDto updateUserProfile(@PathVariable("userId") Long userId,
											 @Valid @RequestBody UserRequestDto userProfile) {
		try {
			return userService.update(userId, userProfile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// http://localhost:8080/users/{userId}/task
	@PostMapping("{userId}/task")
	public TaskResponseDto addTask(@PathVariable("userId") Long userId,
								   @Valid @RequestBody TaskRequestDto taskDto) {
		try {
			return taskService.addTask(userId, taskDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
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
