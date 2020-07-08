package com.umss.todo.common.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umss.todo.common.dto.request.UserCredentialsDto;
import com.umss.todo.common.dto.response.UserResponseDto;
import com.umss.todo.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.umss.todo.common.constants.JwtConstants.*;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private UserService userService;
	
	
	public JwtUsernameAndPasswordAuthenticationFilter(
			AuthenticationManager authenticationManager,
			UserService userService) {
		
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
            UserCredentialsDto authenticationRequest = new ObjectMapper()
                    								   .readValue(request.getInputStream(), UserCredentialsDto.class);
            

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );

            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}


	@Override
	protected void successfulAuthentication(
			HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		UserResponseDto foundUser = userService.findByEmail(authResult.getName());
		
		String tokenBase64Bytes = Base64Utils.encodeToString(JWT_TOKEN_SECRET.getBytes());
				
		String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .claim(JWT_AUTHENTICATED_USER_HEADER, foundUser.getId())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(JWT_TOKEN_EXPIRATION_AFTER_DAYS)))
                .signWith(SignatureAlgorithm.HS512, tokenBase64Bytes.getBytes())
                .compact();

//        response.addHeader(HEADER_STRING, jwtConfig.getPrefix() + token);
		
		Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("user", foundUser);
        body.put("message", String.format("Hi %s, you have logged in successfully!", authResult.getName()));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
	}
}
