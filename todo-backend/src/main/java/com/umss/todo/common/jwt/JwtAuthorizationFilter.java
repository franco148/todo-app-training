package com.umss.todo.common.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.Base64Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import static com.umss.todo.common.constants.JwtConstants.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String requestHeader = request.getHeader(JWT_TOKEN_HEADER);

		if (!hasAuthorizationHeader(requestHeader)) {
			chain.doFilter(request, response);
			return;
		}
		
		String token = requestHeader.replace(JWT_TOKEN_PREFIX, "");
		
		try {
			String tokenBase64Bytes = Base64Utils.encodeToString(JWT_TOKEN_SECRET.getBytes());
			
			Jws<Claims> claimsJws = Jwts.parser()
									.setSigningKey(tokenBase64Bytes.getBytes())
									.parseClaimsJws(token);
			
			Claims body = claimsJws.getBody();
			
			String userName = body.getSubject();
			
			var authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userName,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (JwtException e) {
			throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
		}
		
		chain.doFilter(request, response);
	}
	
	private boolean hasAuthorizationHeader(String header) {
		return header != null && header.startsWith(JWT_TOKEN_PREFIX);
	}
}
