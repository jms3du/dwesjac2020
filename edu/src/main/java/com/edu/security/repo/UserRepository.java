package com.edu.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.security.model.User;


public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByUsername(String username);
}
