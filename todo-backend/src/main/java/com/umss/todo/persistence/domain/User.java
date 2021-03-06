package com.umss.todo.persistence.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User { 
	
	@Id
	@SequenceGenerator(name = "todoSequenceGenerator", sequenceName = "todoSeqGen", initialValue = 6)
	@GeneratedValue(generator = "todoSequenceGenerator")
	private Long id;
	@Column(length = 20)
	private String firstName;
	@Column(length = 50)
	private String lastName;
	@Column(length = 20)
	private String nickName;
	@Column(nullable = false, unique = true, updatable = false, length = 50)
	private String email;
	@Column(nullable = false, length = 200)
	private String password;
	@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_user", nullable = false)
	private Set<Task> tasks = new HashSet<>();
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Task> getTasks() {
		return tasks;
	}
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}	
	public void addTask(Task task) {
		this.tasks.add(task);
	}
}
