package com.umss.todo.common.constants;

public class JwtConstants {

	private JwtConstants() {		
	}
	
	public static final String JWT_TOKEN_PREFIX = "Bearer ";
	public static final String JWT_TOKEN_HEADER = "Authorization";
	public static final int JWT_TOKEN_EXPIRATION_AFTER_DAYS = 3;
	public static final String JWT_TOKEN_SECRET = "My.Jwt.Secret.Key.0ca5d050-6595-11ea-bc55-0242ac130003";
	public static final String JWT_AUTHENTICATED_USER_HEADER = "AuthUserId";
}
