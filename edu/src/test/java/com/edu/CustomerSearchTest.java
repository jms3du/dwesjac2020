package com.edu;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import com.edu.model.entity.Customer;
import com.edu.model.repo.CustomerRepository;
import com.edu.service.impl.CustomerServiceImpl;

import javassist.NotFoundException;

public class CustomerSearchTest {
	
	// Subject Under Test
	private CustomerServiceImpl sut;
	
	// Dependencies
	private EntityManager mockedEM;
	private CustomerRepository mockedRepo;
	
	private Customer mockedCustomer;
	
	@BeforeEach
	private void init() {
		sut = new CustomerServiceImpl();
		mockedCustomer = mock(Customer.class);
		// Mocking dependencies
		mockedEM = mock(EntityManager.class);
		mockedRepo = mock(CustomerRepository.class);
		sut.setCustomerRepository(mockedRepo);
		sut.setEntityManager(mockedEM);
		sut.setLogger(mock(Logger.class));
		
	}

	/*************************
	 *  METHOD TO BE TESTED  *
	 *************************
	 * public Customer findByID(String id) throws NumberFormatException, NotFoundException {
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
	*/
	
	@Test
	public void findByLongIDThrowsNotFoundExWhenNoMatch() {
		try {
			//	Behavior
			Mockito.when(mockedRepo.findById(Mockito.any())).thenReturn(Optional.empty());
			//	Call
			sut.findByID("3");
		} catch (NumberFormatException | NotFoundException e) {
			//	Check
			Assert.isInstanceOf(NotFoundException.class, e);
		} catch (Exception e) {
			throw new AssertionError("Not allowed exception has been thrown");
			
		}
	}
	
	
	@Test
	public void findByLongIDReturnsCustomer() {
		try {
			//	Mock
			mockedCustomer = mock(Customer.class);
			//	Behavior
			Mockito.when(mockedRepo.findById(Mockito.any())).thenReturn(Optional.of(mockedCustomer));
			//	Call
			assert(mockedCustomer == sut.findByID("3"));
		} catch (Exception e) {
			throw new AssertionError("Not allowed exception has been thrown");
			
		}
	}
	
	
	@Test
	public void findByStringIDReturnsOnlyOneCustomer() {
		try {
			//	Mocks
			Query mockedQuery = mock(Query.class);
			Stream<Customer> mockedStream = mock(Stream.class);
			//	Behavior
			when(mockedEM.createNativeQuery(sut.CUSTOM_QUERY, Customer.class)).thenReturn(mockedQuery);
			when(mockedQuery.getResultStream()).thenReturn(mockedStream);
			when(mockedStream.findAny()).thenReturn(Optional.of(mockedCustomer));
			//	Call & check
			assert(mockedCustomer==sut.findByID("lastCustomerSurname"));
		}catch (Exception e) {
			//	Check on fail
			throw new AssertionError("Not allowed exception has been thrown");
			
		}
	}
}
