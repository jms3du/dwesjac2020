package com.edu.security.model.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.edu.security.model.User;
import com.edu.security.model.UserRole;

@Component
public class UserDTOConverter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User fromUserDTOToUser(UserDTO dto) {
		User user  = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRoles(Set.of(UserRole.USER));
		user.setCreateTime(LocalDateTime.now());
		user.setUpdateTime(LocalDateTime.now());
		user.setLastPasswordChange(LocalDateTime.now());
		return user;
		
	}
	
	public UserDTO fromUserToUserDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setUsername(user.getUsername());
		dto.setRoles(user.getRoles());
		return dto;
	}
	
}
