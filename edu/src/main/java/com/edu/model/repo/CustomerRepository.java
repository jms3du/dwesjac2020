package com.edu.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edu.model.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{
	
	
	public Customer findCustomerByNameAndSurname(String name, String surname);
	
	public List<Customer> findCustomerByName(String name);
	
	@Query(value = "select * from customer order by birthDate desc", nativeQuery = true)
	public List<Customer> findAllOrderedByAge();

	
}
