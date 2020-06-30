package com.umss.todo.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umss.todo.reposiroty.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
