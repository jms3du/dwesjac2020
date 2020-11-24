package com.edu.service.impl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.edu.controller.CustomerController;
import com.edu.model.entity.Customer;
import com.edu.model.repo.AddressReprository;
import com.edu.model.repo.CustomerRepository;
import com.edu.model.repo.DocumentRepository;
import com.edu.service.FileHandlerService;
import com.edu.service.IService;

import javassist.NotFoundException;

@MockBean(value = {AddressReprository.class, 
				   DocumentRepository.class, 
				   FileHandlerService.class
				   })
//@ExtendWith(MockitoExtension.class)
@WebMvcTest(CustomerController.class) // This way we route the requests to be tested to this Controller
public class CustomerMockMVCTest {

	@Autowired
	private MockMvc mockMVC; // We use only the web layer, no server, no full context	
	
	@MockBean
	private CustomerRepository cRepository;

	@MockBean
	private IService service; // Explicitly mocked as we set behavior to this service
	
	private final static String ROOT_PATH = "/customer";
	
	@BeforeTestExecution
	private void init() {
		
	}
	
	@Test
	public void getCustomerByIdShouldNotThrowExceptionInAnyCase() throws Exception {
		// We set the service behavior as this: first call throws an Exception; second call returns a new Customer
		Mockito.when(service.findByID(Mockito.anyString()))
				.thenThrow(new NotFoundException("Customer not found"))
				.thenReturn(new Customer());
		
		mockMVC.perform(get(ROOT_PATH+"/3")).andExpect(status().isNotFound());
		mockMVC.perform(get(ROOT_PATH+"/3")).andExpect(status().isOk());
	}

	
	
	@ParameterizedTest
	@MethodSource("postDataProvider")
	public void postNewCustomerWontThrowAnyException(Long id, ResultMatcher expectedResult) throws Exception {
		
		Customer mockCustomer = Mockito.mock(Customer.class);
		when(mockCustomer.getId()).thenReturn(id);
		when(cRepository.save(Mockito.any())).thenReturn(mockCustomer);
			
		mockMVC.perform( MockMvcRequestBuilders.post(ROOT_PATH)
							.accept(MediaType.APPLICATION_JSON_VALUE)
							.contentType(MediaType.APPLICATION_JSON_VALUE)
							.content("{\"name\":\"\", \"surname\":\"\", \"addresses\":[{}]}")
					)
		.andExpect(expectedResult)
		;

	}
	
	private static Stream<Arguments> postDataProvider(){
		return Stream.of(
						Arguments.of(null, status().isBadRequest()),
						Arguments.of(Long.valueOf(3), status().is2xxSuccessful()),
						Arguments.of(Long.valueOf(15), status().isCreated())
					);
	}
}
