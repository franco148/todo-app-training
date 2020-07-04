package com.umss.todo.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umss.todo.common.dto.request.TaskRequestDto;
import com.umss.todo.common.dto.response.TaskResponseDto;
import com.umss.todo.exception.InvalidPropertyValueException;
import com.umss.todo.exception.TaskNotFoundException;
import com.umss.todo.exception.TaskValidationException;
import com.umss.todo.exception.UserNotFoundException;
import com.umss.todo.persistence.domain.Priority;
import com.umss.todo.persistence.domain.State;
import com.umss.todo.persistence.domain.Task;
import com.umss.todo.persistence.domain.User;
import com.umss.todo.persistence.repository.TaskRepository;
import com.umss.todo.persistence.repository.UserRepository;

import static com.umss.todo.exception.TaskValidationException.*;

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
			throw new TaskValidationException(validationErrorKeys);
		}
		
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException(userId);
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
	
	public TaskResponseDto updateTask(Long taskId, TaskRequestDto taskDto) 
			throws TaskValidationException, TaskNotFoundException {
		
		Set<String> validationErrorKeys = validateTaskRequestDto(taskDto);
		if (!validationErrorKeys.isEmpty()) {
			throw new TaskValidationException(validationErrorKeys);
		}
		
		Task taskToEdit = taskRepository.getOne(taskId);
		if (taskToEdit == null) {
			throw new TaskNotFoundException(taskId);
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
	
	public TaskResponseDto updateTaskState(Long taskId, String state) 
			throws InvalidPropertyValueException, TaskNotFoundException {
		
		State newTaskState = State.tryParse(state);
		if (newTaskState == null) {
			throw new InvalidPropertyValueException("STATE", state);
		}
		
		Task existingTask = taskRepository.findById(taskId)
							.orElseThrow(() -> new TaskNotFoundException(taskId));
		
		existingTask.setState(newTaskState);
		if (newTaskState == State.IN_PROGRESS) {
			// When task starts in progress state, set startTime
			existingTask.setStartTime(LocalDateTime.now());
		} else if (newTaskState == State.COMPLETED) {
			// When task ends, the duration is calculated and set to the task
			existingTask.setEndTime(LocalDateTime.now());
			Long hoursDuration = existingTask.getStartTime().until(existingTask.getEndTime(), ChronoUnit.MINUTES);
			existingTask.setDuration(hoursDuration);
		}
		Task updatedTask = taskRepository.save(existingTask);
		
		return modelMapper.map(updatedTask, TaskResponseDto.class);
	}
	
	
	public Set<TaskResponseDto> getUserTasksFilteredBy(Long userId, 
													   LocalDateTime from, 
													   LocalDateTime to, 
													   String priority, 
													   String state) {
		
		/**
		 * SCENARIO 1: priority = HIGH
		 * 
		 * [
		 * 	{title: tarea1, priority: HIGH, state: in_progress},
		 *  {title: tarea2, priority: HIGH, state: cancelled}
		 *  ...
		 *  ...
		 *  ...
		 * ]
		 * 
		 * SCENARIO 2: priority = LOW & state = COMPLETED
		 * [
		 * 	{title: tarea1, priority: LOW, state: COMPLETED},
		 *  {title: tarea2, priority: LOW, state: COMPLETED}
		 * ]
		 * 
		 * SCENARIO 3: state = IN_PROGRESS
		 * 
		 * SCENARIO 4: todooooooooooooo
		 */
		
		return null;
	}
	
	
	public Map<String, Set<TaskResponseDto>> getUserTasksGroupedBy(Long userId, 
																   LocalDateTime from, 
																   LocalDateTime to, 
																   String groupBy) {
		/**
		 * STATE
		 * ==========================================
		 * 
		 * PLANNED: [{taria1}, {tarea2}, {tarea3}......{tareaN}]
		 * IN_PROGRESS: [{tariaB}, {tareaB1}, {tareaB2}......{tareaN}]
		 * COMPLETED: [{tariaC}, {tarea2}, {tarea3}......{tareaN}]
		 * ...
		 * ...
		 * 
		 * 
		 * [
   "PLANNED":   [
      {
         taria1
      },
      {
         tarea2
      },
      {
         tarea3
      },
      {
         "tareaN"
      }
   ],
   "IN_PROGRESS":   [
      {
         "tariaB"
      },
      {
         tareaB1
      },
      {
         tareaB2
      }      "......"      {
         "tareaN"
      }
   ],
   "COMPLETED":   [
      {
         "tariaC"
      },
      {
         tarea2
      },
      {
         tarea3
      },
      {
         "tareaN"
      }
   ]
]
		 * 
		 */
		return null;
	}
	
	
	private Set<String> validateTaskRequestDto(TaskRequestDto taskDto) {
		Set<String> validationErrors = new HashSet<>();
		
		// If startTime and endTime of a TaskDto are not null, startTime should not be AFTER startTime
		if (taskDto.getStartTime() != null && taskDto.getEndTime() != null &&
			!taskDto.getEndTime().isAfter(taskDto.getStartTime())) {
			validationErrors.add(START_END_DATETIME_FIELD);
		}
		
		try {
			Priority.valueOf(taskDto.getPriority());
		} catch (Exception e) {
			validationErrors.add(PRIORITY_FIELD);
		}
		
		try {
			State.valueOf(taskDto.getState());
		} catch (Exception e) {
			validationErrors.add(STATE_FIELD);
		}
		
		return validationErrors;
	}
}
