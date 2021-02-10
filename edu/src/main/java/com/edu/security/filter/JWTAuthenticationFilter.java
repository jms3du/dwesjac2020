package com.edu.security.filter;

import static com.edu.security.common.SecurityConstants.HEADER_STRING;
import static com.edu.security.common.SecurityConstants.TOKEN_PREFIX;
import static com.edu.security.filter.jwt.JWTTokenProvider.generateToken;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.edu.security.common.SecurityConstants;
import com.edu.security.model.User;
import com.edu.security.model.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebFilter
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstants.LOG_IN);
    }
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		UserDTO user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
			

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return 	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), 
																						   user.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + generateToken(((User)authResult.getPrincipal())));
		
	}
	
	
	

}
