package com.edu.service.impl;

import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.model.entity.Customer;
import com.edu.model.repo.CustomerRepository;
import com.edu.service.IService;

import javassist.NotFoundException;

@Service
public class CustomerServiceImpl implements IService<Customer>{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CustomerRepository repository;
	
	public Customer findByName(String name) {
		Query query = entityManager
				.createNativeQuery("Select * "
									+ "from Customer "
									+ "where lower(name) "
									+ "like ?1"
								, Customer.class);
		query.setParameter(1, name);
		Stream<Customer> customers = query.getResultStream();
		entityManager.close();
		
		return customers.findAny().orElse(null);
		
	}


	public Customer findByID(String id) throws NumberFormatException, NotFoundException {
		Customer customer = null;
		if(id!=null && id.matches(NUMBER_REG)) {
			customer = repository.findById(Long.valueOf(id)).orElseThrow(()-> new NotFoundException("Element not found"));
		}else {
			customer = this.findByName(id);
		}
		return customer;
		
	}
	
	
	 
}
