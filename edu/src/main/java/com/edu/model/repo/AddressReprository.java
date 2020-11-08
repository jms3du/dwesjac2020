package com.edu.model.repo;

import org.springframework.data.repository.CrudRepository;

import com.edu.model.entity.Address;

public interface AddressReprository extends CrudRepository<Address, Long> {

}
