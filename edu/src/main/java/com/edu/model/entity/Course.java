package com.edu.model.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Course extends BasicInfo{

	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "category_id_fk"), name = "category_id")
	private Category category;
	

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
