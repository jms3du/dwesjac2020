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
		// Recall
		//passwordEncoder.matches("1234", "$2a$10$klljZRCKUsItpuhhWjt/OOuQotVva/ADfOQDBtWNIJBBX5d88sZNm");
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRoles(Set.of(UserRole.USER));
		user.setCreateTime(LocalDateTime.now());
		user.setUpdateTime(LocalDateTime.now());
		user.setLastPasswordChange(LocalDateTime.now());
		user.setLocked(false);
		user.setEnabled(true);
		user.setAuthenticationAttempts(0);
		user.setPasswordPolicyExpDate(LocalDateTime.now().plusDays(180));
		return user;
		
	}
	
	public UserDTO fromUserToUserDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setUsername(user.getUsername());
		dto.setRoles(user.getRoles());
		return dto;
	}
	
}
