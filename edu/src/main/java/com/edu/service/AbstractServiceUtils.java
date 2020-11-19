package com.edu.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractServiceUtils {

	protected Logger logger;
	
	
	@PostConstruct
	private void initLogger() {
		logger = LoggerFactory.getLogger(getClass());
	}

}
