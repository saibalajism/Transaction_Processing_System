package com.transaction.transaction_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.transaction_service.dto.fraudRequestDto;
import com.transaction.transaction_service.dto.fraudResponseDto;
import com.transaction.transaction_service.dto.transactionCreateDto;
import com.transaction.transaction_service.entity.transactionCreateEntity;
import com.transaction.transaction_service.service.transactionCreateService;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;

@RestController
@RequestMapping("/transaction")
public class transactionCreateController {
	
	@Autowired
	private transactionCreateService transactService;
	
	
	@PostMapping("/create")
	public transactionCreateEntity createTransaction(@RequestBody transactionCreateDto dto) {
		return transactService.createTransaction(dto);
	}

	
	@PostMapping("/fraudValidate")
	public fraudResponseDto findByfromAccount(@RequestBody fraudRequestDto FromAcc) {
		fraudResponseDto response = transactService.findFromAccount(FromAcc);
		if (response == null) {
	        response = new fraudResponseDto();
	        response.setAvgAmount(0.0f);
	        response.setCount(0L);
	    }
		return response;
	}

}
