package com.edu.service.impl;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.edu.model.entity.Customer;
import com.edu.model.entity.Document;
import com.edu.model.repo.CustomerRepository;
import com.edu.model.repo.DocumentRepository;
import com.edu.service.FileHandlerService;


public class CustomerDocumentHandlerTest {
	
	/**
	 * 
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
		}return c;
	}
	 */

	private CustomerServiceImpl sut;
	
	private CustomerRepository mockCustomerRepo;
	private DocumentRepository mockDocumentRepo;
	private FileHandlerService mockFileHandlerService;
	private Logger mockLogger;
	
	
	private MultipartFile mockMPF;
	private Document mockDocument;
	private Customer mockCustomer;
	private Blob mockBlob;
	
	
	@BeforeEach
	private void init() {
		
		mockCustomerRepo = mock(CustomerRepository.class);
		mockDocumentRepo = mock(DocumentRepository.class);
		mockFileHandlerService = mock(FileHandlerService.class);
		mockLogger = mock(Logger.class);
		
		mockBlob = mock(Blob.class);
		mockDocument = mock(Document.class);
		mockCustomer = mock(Customer.class);
		mockMPF = mock(MultipartFile.class);
		
		when(mockFileHandlerService.createBlob(mockMPF)).thenReturn(mockBlob);
		when(mockMPF.getName()).thenReturn("Nombre de archivo");
		when(mockDocumentRepo.save(Mockito.any(Document.class))).thenReturn(mockDocument);
		
		
		sut = new CustomerServiceImpl(mockCustomerRepo, mockDocumentRepo);
		sut.setLogger(mockLogger);
		sut.setFhService(mockFileHandlerService);
		
		
	}
	
	
	@Test
	public void addingDocumentToCustomerSuccessfully() {
		
		
		
		List<Document> documents = new ArrayList<>();
		
		when(mockCustomerRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockCustomer));
		when(mockCustomer.getDocuments()).thenReturn(documents);
		
		Customer c = sut.addDocument("1", mockMPF);
		
		verify(mockCustomer, atMost(3)).getDocuments();
		verify(mockDocumentRepo, atMostOnce()).save(Mockito.any(Document.class));
		
		assert(c.getDocuments().contains(mockDocument));
	}
	
	
	
	@Test
	public void addingDocumentToNonExistingCustomerThrowsExcepcion() {
		
		when(mockCustomerRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		Customer c = sut.addDocument("1", mockMPF);
		assert(c==null);
		Mockito.verify(mockLogger, atMostOnce()).debug(Mockito.anyString());
		Mockito.verify(mockCustomerRepo, atMostOnce()).findById(Mockito.anyLong());		
	}
	
}
