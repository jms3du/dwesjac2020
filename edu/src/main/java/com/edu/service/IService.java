package com.edu.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;

@Service
public interface IService<T> {
	
	public static final String NUMBER_REG = "[0-9]*";
	
	public T findByID(String id) throws NumberFormatException, NotFoundException;
	
	public T findByName(String name) throws NotFoundException;
	
	/**
	 * Add a new Document to this entity
	 * @param id Identifier of the entity to be updated
	 * @param mpf Multipart file 
	 * @return Updated entity
	 */
	public T addDocument(String id, MultipartFile mpf);
	
}
