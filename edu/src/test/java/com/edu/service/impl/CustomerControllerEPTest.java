package com.edu.service.impl;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.edu.controller.CustomerController;
import com.edu.model.entity.Address;
import com.edu.model.entity.Customer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerControllerEPTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	CustomerController sut;
	
	@Autowired
	private TestRestTemplate template;
	
	private final String BASE_URL = "http://localhost:%s/customer";

	
	@Test
	public void retrieveCustomers() {
		assert(template.getForEntity(getURL(), Customer.class).getBody()!=null);
	}
	
	
	@Test
	public void createCustomer() {
		Customer customer = new Customer() {{ 
												setName("JM"); 
												setSurname("S");
												setAddresses(new ArrayList<>() {{add(new Address());}});
											}};
		
		Long id = template.postForObject(getURL(), customer, Long.class);
		
		assert(template.getForEntity(getURL()+"/"+id, Customer.class).getBody()!=null);
		
	}
	
	private String getURL() {
		return String.format(BASE_URL, port);
	}
}
