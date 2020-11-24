package com.edu.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.edu.controller.CustomerController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerControllerEPTest {

	@Autowired
	CustomerController sut;
	
	@Autowired
	private TestRestTemplate template;
	
	
	@Test
	public void retrieveCustomers() {
		template.getForEntity("", null);
	}
	
}
