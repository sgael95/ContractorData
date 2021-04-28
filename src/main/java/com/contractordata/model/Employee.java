package com.contractordata.model;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
	
	private final UUID employeeId;
	
	@NotBlank
	private final String firstName;
	private final String lastName;
	private final String address;
	private final int phoneNumber;

	public Employee(@JsonProperty("id") UUID employeeId,
			@JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("address") String address, 
			@JsonProperty("phoneNumber") int phoneNumber) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	public UUID getEmployeeId() {
		return employeeId;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}
	
}
