package com.edu.service;

import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public interface IService<T> {
	
	public static final String NUMBER_REG = "[0-9]*";
	
	public T findByID(String id) throws NumberFormatException, NotFoundException;
	
	public T findByName(String name) throws NotFoundException;
	
}
