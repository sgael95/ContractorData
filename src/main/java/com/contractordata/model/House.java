package com.contractordata.model;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;


public class House {
	
	private final UUID houseId;
	
	@NotBlank
	private final String address;
	private final int size;
	private final double amountPaid;
	
	public House(@JsonProperty("id") UUID houseId,
			@JsonProperty("address") String address,
			@JsonProperty("size") int size,
			@JsonProperty("amount paid") double amountPaid) {
		this.houseId = houseId;
		this.address = address;
		this.size = size;
		this.amountPaid = amountPaid;
	}

	public UUID getHouseId() {
		return houseId;
	}

	public String getAddress() {
		return address;
	}

	public int getSize() {
		return size;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

}
