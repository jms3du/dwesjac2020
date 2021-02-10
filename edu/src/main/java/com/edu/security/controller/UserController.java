package com.edu.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.edu.security.model.dto.UserDTO;
import com.edu.security.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/sign-up")
	public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO userDTO) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(userDTO));
		}catch(Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO){
		// Created only to retrieve the Bearer token once authenticated
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
	}
}
