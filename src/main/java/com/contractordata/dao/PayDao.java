package com.contractordata.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.contractordata.model.Pay;

public interface PayDao {

	int insertPayment(UUID payId, Pay pay);
	
	default int insertPayment(Pay pay) {
		UUID id = UUID.randomUUID();
		return insertPayment(id, pay);
	}
	
	List<Pay> selectAllPayments();
	
	Optional<Pay> selectPaymentById(UUID id);
	
	int deletePayment(UUID id);
	
	int updatePayment(UUID id, Pay pay);
}
