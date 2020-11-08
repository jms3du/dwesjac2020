package com.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.model.entity.Address;
import com.edu.model.entity.Customer;
import com.edu.model.repo.CustomerRepository;
import com.edu.service.FileHandlerService;
import com.edu.service.IService;

import javassist.NotFoundException;

@RestController
public class CustomerController {
	
	

	@Autowired
	private CrudRepository<Address, Long> addressRepository;
	
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Autowired
	private IService<Customer> service;

	@Autowired
	private FileHandlerService fileService;
	
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
		addressRepository.save(new Address());
		customer.getAddresses().forEach(ad -> addressRepository.save(ad));
		Customer c = customerRepository.save(customer);
		return c.getId()!=null? 
				ResponseEntity.ok(customer.getId())
				:	ResponseEntity.badRequest().build();
	}
	
	
	/**
	 * 
	 * @param pic File to store
	 * @param id  Customer whose documents would be updated
	 * @param redirectAttributes
	 * @return
	 */
	@PutMapping("/customer/{id}/pic")
	public ResponseEntity<?> uploadPicture(@RequestParam("pic") MultipartFile pic, 
								@PathVariable(required = false) String id,
								RedirectAttributes redirectAttributes){
		
		service.addDocument(id, pic);
		return ResponseEntity.ok("File "+ pic.getOriginalFilename()+ "successfully uploaded");
	}

}
