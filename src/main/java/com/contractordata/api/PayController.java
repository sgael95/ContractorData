package com.contractordata.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contractordata.model.Pay;
import com.contractordata.service.PayService;

@RequestMapping("api/v1/pay")
@RestController
public class PayController {
	
	private final PayService payService;
	
	@Autowired
	public PayController(PayService payService) {
		this.payService = payService;
	}
	
	@PostMapping
	public void addPay(@Valid @NotNull @RequestBody Pay pay) {
		payService.addPayment(pay);
	}
	
	@GetMapping
	public List<Pay> getPay() {
		return payService.getAllPayments();
	}
	
	@GetMapping(path = "{id}")
	public Pay getPayById(@PathVariable("id") UUID id) {
		return payService.getPaymentById(id)
				.orElse(null);
	}
	
	@DeleteMapping(path = "{id}")
	public void deletePayById(@PathVariable("id") UUID id) {
		payService.deletePayment(id);
	}
	
	@PutMapping(path = "{id}")
	public void updatePay(@Valid @NotNull @PathVariable("id") UUID id,
			@RequestBody Pay paymentToUpdate) {
		payService.updatePayment(id, paymentToUpdate);
	}

}
