package com.transaction.fraud_service.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.transaction.fraud_service.dto.transactionDetailsRequestDto;
import com.transaction.fraud_service.dto.transactionResponseDto;

@HttpExchange
public interface transactionCreateClient {

	@PostExchange("/transaction/fraudValidate")
	transactionResponseDto findByfromAccount(@RequestBody transactionDetailsRequestDto data);
	
	

}
