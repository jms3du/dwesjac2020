package com.edu.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class GetMessage {
	
	@Autowired
	private SorterService service;
	@Autowired
	private Logger logger;
	
	

	
	public GetMessage() {
		// TODO Auto-generated constructor stub
	}

	public String getSortedAndSeparatedNames(List<String> lista) {
		
		lista = (List<String>)service.sort(lista);
		logger.info("lista separada");
		
		String result="";
		
		for(String s : lista) {
			result+=s+",";
		}
		
		return result;
	}

	public void setService(SorterService service) {
		this.service = service;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	
	
}
