package com.edu.model.repo;

import org.springframework.data.repository.CrudRepository;

import com.edu.model.entity.Document;

public interface DocumentRepository extends CrudRepository<Document, Long> {

}
