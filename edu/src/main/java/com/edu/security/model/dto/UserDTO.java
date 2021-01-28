package com.edu.security.model.dto;

import java.util.Set;

import com.edu.security.model.UserRole;

public class UserDTO {
	
	private String username;
	
	private String password;
	
	private Set<UserRole> roles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

}
