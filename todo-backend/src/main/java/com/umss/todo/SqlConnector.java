package com.umss.todo;

import java.util.List;

/**
 * clase abstracta.
 * NO se puede heredar mas de una vez
 * puedo tener metodos no abstractos
 * puede tener metodos abstractos
 * podemos tener atributos private, protected, public, etc
 * podemos tener constantes
 * 
 * 
 * interface.
 * SI se puede implementar mas de una interfaz
 * NO puede haber metodos no abstractos
 * SOLO TIENE METODOS ABSTRACTOS
 * Todo lo que se declare en una interfaz, es static public y final ()
 */
public interface SqlConnector {

	// Attibutes
	String SELECT_ALL_USERS = "SELECT * FROM users";
	
	// Methods
	List<User> getAllUsers();
}
