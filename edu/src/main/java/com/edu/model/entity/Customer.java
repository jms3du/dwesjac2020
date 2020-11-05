package com.edu.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ConstraintMode;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

@Entity
@DiscriminatorValue(value = "0")
public class Customer {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String surname;
	
	@NonNull
	@DateTimeFormat(pattern = "ddMMyyyy")
	private LocalDate birthDate;
	
	@OneToMany
	@JoinColumn(name="address_id", foreignKey = @ForeignKey(name="addres_id_fk"), nullable = false)
	private List<Address> addresses;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="nicks", joinColumns = @JoinColumn(referencedColumnName = "id"))
	private List<String> nicks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<String> getNicks() {
		return nicks;
	}

	public void setNicks(List<String> nicks) {
		this.nicks = nicks;
	}

}
