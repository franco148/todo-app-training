package com.umss.todo.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umss.todo.common.dto.request.TaskRequestDto;
import com.umss.todo.common.dto.response.TaskResponseDto;
import com.umss.todo.persistence.domain.Priority;
import com.umss.todo.persistence.domain.State;
import com.umss.todo.persistence.domain.Task;
import com.umss.todo.persistence.domain.User;
import com.umss.todo.persistence.repository.TaskRepository;
import com.umss.todo.persistence.repository.UserRepository;

@Service
public class TaskService {
	
	private UserRepository userRepository;
	private TaskRepository taskRepository;
	private ModelMapper modelMapper;
	
	
	@Autowired
	public TaskService(TaskRepository taskRepository,
					   UserRepository userRepository,
					   ModelMapper modelMapper) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	

	public TaskResponseDto addTask(Long userId, TaskRequestDto taskDto) throws Exception {
		Set<String> validationErrorKeys = validateTaskRequestDto(taskDto);
		if (!validationErrorKeys.isEmpty()) {
			throw new Exception("The following errors happened");
		}
		
		if (!userRepository.existsById(userId)) {
			throw new Exception("User with ID=" + userId + " does not exist.");
		}
		
		Task taskToAdd = modelMapper.map(taskDto, Task.class);
		User taskOwner = userRepository.getOne(userId);
		taskOwner.addTask(taskToAdd);
		userRepository.save(taskOwner);
		
		TaskResponseDto addedTaskDto = taskOwner.getTasks().stream()
										.sorted(Comparator.comparing(Task::getCreatedAt).reversed())
										.findFirst()
										.map(task -> modelMapper.map(task, TaskResponseDto.class)).get();
			
		return addedTaskDto;
	}
	
	public TaskResponseDto updateTask(Long taskId, TaskRequestDto taskDto) throws Exception {
		Set<String> validationErrorKeys = validateTaskRequestDto(taskDto);
		if (!validationErrorKeys.isEmpty()) {
			throw new Exception();
		}
		
		Task taskToEdit = taskRepository.getOne(taskId);
		if (taskToEdit == null) {
			throw new Exception();
		}
		
		State taskState = State.tryParse(taskDto.getState());
		Priority taskPriority = Priority.tryParse(taskDto.getPriority());
		
		taskToEdit.setTitle(taskDto.getTitle());
		taskToEdit.setDescription(taskDto.getDescription());		
		taskToEdit.setState(taskState);		
		taskToEdit.setPriority(taskPriority);
		
		Task editedTask = taskRepository.save(taskToEdit);
		
		return modelMapper.map(editedTask, TaskResponseDto.class);
	}
	
	public TaskResponseDto updateTaskState(Long taskId, String state) throws Exception {
		State newTaskState = State.tryParse(state);
		if (newTaskState == null) {
			throw new Exception("State=" + state + " is not valid.");
		}
		
		Task existingTask = taskRepository.findById(taskId)
							.orElseThrow(() -> new Exception("Task could not be found."));
		
		existingTask.setState(newTaskState);
		Task updatedTask = taskRepository.save(existingTask);
		
		return modelMapper.map(updatedTask, TaskResponseDto.class);
	}
	
	
	public Set<TaskResponseDto> getUserTasksFilteredBy(Long userId, 
													   LocalDateTime from, 
													   LocalDateTime to, 
													   String priority, 
													   String state) {
		return null;
	}
	
	
	public Map<String, Set<TaskResponseDto>> getUserTasksGroupedBy(Long userId, 
																   LocalDateTime from, 
																   LocalDateTime to, 
																   String groupBy) {
		return null;
	}
	
	
	private Set<String> validateTaskRequestDto(TaskRequestDto taskDto) {
		Set<String> validationErrors = new HashSet<>();
		
		// If startTime and endTime of a TaskDto are not null, startTime should not be AFTER startTime
		if (taskDto.getStartTime() != null && taskDto.getEndTime() != null &&
			!taskDto.getEndTime().isAfter(taskDto.getStartTime())) {
			validationErrors.add("StartTime should no be greather than EndTime");
		}
		
		try {
			Priority.valueOf(taskDto.getPriority());
		} catch (Exception e) {
			validationErrors.add("Invalid value for PRIORITY");
		}
		
		try {
			State.valueOf(taskDto.getState());
		} catch (Exception e) {
			validationErrors.add("Invalid value for STATE");
		}
		
		return validationErrors;
	}
}
