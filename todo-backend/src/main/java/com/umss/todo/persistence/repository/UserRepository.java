package com.umss.todo.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umss.todo.persistence.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
