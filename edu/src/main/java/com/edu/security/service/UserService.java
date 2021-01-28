package com.edu.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edu.security.model.User;
import com.edu.security.model.dto.UserDTO;
import com.edu.security.model.dto.UserDTOConverter;
import com.edu.security.repo.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserDTOConverter converter;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("Username not found"));
	}
	
	
	public User createNewUser(UserDTO userDTO) {
		return repository.save(converter.fromUserDTOToUser(userDTO)); 
	}
	

}
