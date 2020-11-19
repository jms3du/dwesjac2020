package com.edu.service.impl;

import java.util.ArrayList;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.model.entity.Customer;
import com.edu.model.entity.Document;
import com.edu.model.repo.CustomerRepository;
import com.edu.model.repo.DocumentRepository;
import com.edu.service.AbstractServiceUtils;
import com.edu.service.FileHandlerService;
import com.edu.service.IService;

import javassist.NotFoundException;

@Service
public class CustomerServiceImpl extends AbstractServiceUtils implements IService<Customer>{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private DocumentRepository docReposiroty;
	
	@Autowired
	private FileHandlerService fhService;
	
	public final static String CUSTOM_QUERY = "Select * "
													+ "from Customer "
													+ "where lower(name) "
													+ "like ?1";
	
	public Customer findByName(String name) {
		Query query = entityManager
				.createNativeQuery(CUSTOM_QUERY, Customer.class);
		query.setParameter(1, name);
		Stream<Customer> customers = query.getResultStream();
		entityManager.close();
		
		return customers.findAny().orElse(null);
		
	}


	public Customer findByID(String id) throws NumberFormatException, NotFoundException {
		Customer customer = null;
		if(id!=null && id.matches(NUMBER_REG)) {
			logger.info(String.format("Looking for customer by id: %s", id));
			customer = customerRepository.findById(Long.valueOf(id)).orElseThrow(()-> new NotFoundException("Element not found"));
		}else {
			customer = this.findByName(id);
			logger.info(String.format("Looking for customer by name: %s", id));
		}
		return customer;
		
	}


	@Override
	public Customer addDocument(String id, MultipartFile mpf) {
		Customer c = null;
		
		try {
			Document doc = docReposiroty.save( new Document(fhService.createBlob(mpf), 
															 mpf.getName(), 
															 Integer.valueOf((int) mpf.getSize()))
										);
			
			c = findByID(id);
			c.setDocuments(c.getDocuments()!=null && !c.getDocuments().isEmpty()? c.getDocuments():new ArrayList<>());
			c.getDocuments().add(doc);
			customerRepository.save(c);
			
		} catch (NumberFormatException | NotFoundException e) {
			logger.debug(String.format("Customer with identifier %s could not be found ", id));
		}
			
		
		return c;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	 
}
