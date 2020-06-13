package com.umss.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MySqlUserConnector implements SqlConnector {
	
	private String mysqlConnectionString = "jdbc:mysql://127.0.0.1:3306/tododb";
	private String mysqlUserName = "todouser";
	private String mysqlUserPassword = "password01";
	
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	@Override
	public List<User> getAllUsers() {
		List<User> listUsersResponse = new ArrayList<User>();
		
		try {
			// 1. Get a connection to the database
			connection = DriverManager.getConnection(mysqlConnectionString, mysqlUserName, mysqlUserPassword);
			// 2. Create a statement
			statement = connection.createStatement();
			// 3. Execute the SQL query
			String selectAllUsersQuery = "SELECT * FROM users";
			resultSet = statement.executeQuery(selectAllUsersQuery);
			// 4. Process the result
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong("id"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				
				listUsersResponse.add(user);
			}			
			
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return listUsersResponse;
	}
}
