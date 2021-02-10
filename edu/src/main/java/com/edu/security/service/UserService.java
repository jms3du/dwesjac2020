package com.edu.security.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
	
	public UserDetails loadUserById(Long idUser) throws AuthenticationException {
		return repository.findById(idUser)
				.orElseThrow(()-> new AuthenticationException("Id/username not found"));
	}	
	
	public UserDTO createNewUser(UserDTO userDTO) {
		return converter.fromUserToUserDTO(repository.save(converter.fromUserDTOToUser(userDTO))); 
	}
	

}
