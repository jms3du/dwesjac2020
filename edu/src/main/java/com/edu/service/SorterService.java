package com.edu.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SorterService {
	
	
	
	public Collection<String> sort(List<String> lista){
		Collections.sort(lista);
		return lista;
	}

	
	
}
