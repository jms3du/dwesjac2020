package com.edu.model.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlEnum;

public enum CategoryType {
	
	STATISTICS(0),
	COMPUTING(1),
	LANGUAGES(2),
	PROGRAMMING(3),
	OTHER(4);

	CategoryType(int i) {}

}
