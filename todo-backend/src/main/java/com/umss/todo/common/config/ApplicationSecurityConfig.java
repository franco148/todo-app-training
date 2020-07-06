package com.umss.todo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.umss.todo.common.jwt.JwtAuthorizationFilter;
import com.umss.todo.common.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.umss.todo.service.UserService;

@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private PasswordEncoder passwordEncoder;
	private UserService userService;
	
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*", "/h2-console/**").permitAll()
		.antMatchers(HttpMethod.POST, "/users").permitAll()
		.anyRequest().authenticated()
		.and()
			.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), userService))
			.addFilterAfter(new JwtAuthorizationFilter(authenticationManager()), JwtUsernameAndPasswordAuthenticationFilter.class)
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.headers().frameOptions().disable();
	}	
}
