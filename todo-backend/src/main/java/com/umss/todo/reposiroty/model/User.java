package com.umss.todo.reposiroty.model;

import java.util.HashSet;
import java.util.Set;

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
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	@Column(nullable = false, length = 200)
	private String password;
	@OneToMany(mappedBy = "user")
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
	
	
}
