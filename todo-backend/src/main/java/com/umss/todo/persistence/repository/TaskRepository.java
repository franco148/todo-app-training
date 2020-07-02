package com.umss.todo.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umss.todo.persistence.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
