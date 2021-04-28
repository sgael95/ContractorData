package com.contractordata.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pay {

	private final UUID payId;
	
	private final UUID houseId;
	private final UUID employeeId;
	private final double amountPaid;
	
	public Pay(@JsonProperty("id") UUID payId,
			@JsonProperty("houseId") UUID houseId,
			@JsonProperty("employeeId") UUID employeeId,
			@JsonProperty("amountPaid") double amountPaid) {
		this.payId = payId;
		this.houseId = houseId;
		this.employeeId = employeeId;
		this.amountPaid = amountPaid;
	}

	public UUID getPayId() {
		return payId;
	}

	public UUID getHouseId() {
		return houseId;
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public double getAmountPaid() {
		return amountPaid;
	}
	
}