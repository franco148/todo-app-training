package com.umss.todo.controller;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umss.todo.common.dto.TaskResponseDto;
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
	public TaskResponseDto changeTaskState(
			@PathVariable("taskId") Long taskId,
			@RequestParam("state") @Pattern(regexp = "PLANNED|PAUSED|IN_PROGRESS|CANCELLED|COMPLETED", flags = Flag.CANON_EQ) String taskState) {
		try {
			return taskService.updateTaskState(taskId, taskState);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
