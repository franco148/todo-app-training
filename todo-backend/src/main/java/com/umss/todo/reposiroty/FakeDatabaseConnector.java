package com.umss.todo.reposiroty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.umss.todo.reposiroty.model.User;

@Repository("fake")
public class FakeDatabaseConnector implements SqlConnector {

	private List<User> fakeDatabaseCollection;
	
	
	public FakeDatabaseConnector() {
		this.fakeDatabaseCollection = new ArrayList<>();
		User user = new User();
		user.setId(1L);
		user.setFirstName("Test");
		user.setLastName("Example");
		user.setAccount("test.example@gmail.com");
		user.setPassword("123456");
		fakeDatabaseCollection.add(user);
		User user2 = new User();
		user2.setId(2L);
		user2.setFirstName("Raúl");
		user2.setLastName("Barrientos");
		user2.setAccount("raul.barrientos@gmail.com");
		user2.setPassword("asdfghjklñ");
		fakeDatabaseCollection.add(user2);
	}
	
	
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return this.fakeDatabaseCollection;
	}
	
	public void save(User newUser) {
		this.fakeDatabaseCollection.add(newUser);
	}
}
