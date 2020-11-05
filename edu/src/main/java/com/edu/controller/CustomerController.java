package com.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edu.model.entity.Address;
import com.edu.model.entity.Customer;
import com.edu.model.repo.CustomerRepository;
import com.edu.service.IService;

import javassist.NotFoundException;

@RestController
public class CustomerController {
	
	
	@Autowired
	private CrudRepository<Customer, Long> customerRepository;
	
	@Autowired(required=false)
	private CrudRepository<Address, Long> addressRepository;
	
	
	@Autowired
	private CustomerRepository customerRepository2;
	
	
	@Autowired
	private IService<Customer> service;

	
	@GetMapping("/customer/{id}")
	public ResponseEntity<?> getCustomers(@PathVariable(required = false) String id){
		try {
			return ResponseEntity.ok(service.findByID(id));
		} catch (NumberFormatException | NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@PostMapping("/customer")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
		customer.getAddresses().forEach(ad -> addressRepository.save(ad));
		Customer c = customerRepository.save(customer);
		return c.getId()!=null? ResponseEntity.ok(customer.getId()): ResponseEntity.badRequest().build();
	}

}
