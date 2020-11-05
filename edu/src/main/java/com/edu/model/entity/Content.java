package com.edu.model.entity;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Content extends BasicInfo {
	
	@OneToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "ccourse_id_fk"	))
	private Course course;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
