package com.contractordata.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.contractordata.dao.PayDao;
import com.contractordata.model.Pay;

@Service
public class PayService {

	private final PayDao payDao;
	
	@Autowired
	public PayService(@Qualifier("payPostgres") PayDao payDao) {
		this.payDao = payDao;
	}
	
	public int addPayment(Pay pay) {
		return payDao.insertPayment(pay);
	}
	
	public List<Pay> getAllPayments() {
		return payDao.selectAllPayments();
	}
	
	public Optional<Pay> getPaymentById(UUID id) {
		return payDao.selectPaymentById(id);
	}
	
	public int deletePayment(UUID id) {
		return payDao.deletePayment(id);
	}
	
	public int updatePayment(UUID id, Pay paymentToUpdate) {
		return payDao.updatePayment(id, paymentToUpdate);
	}
}
