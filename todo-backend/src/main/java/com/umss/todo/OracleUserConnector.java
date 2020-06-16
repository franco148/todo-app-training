package com.umss.todo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("oracle")
public class OracleUserConnector implements SqlConnector {

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		// TODA LA LOGICA NECESARIA PARA TRABAJAR CON ORACLE
		// ...
		// ...
		System.out.println("Obtaining results from Oracle Database");
		return new ArrayList<User>();
	}

}
