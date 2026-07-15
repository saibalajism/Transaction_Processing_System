package com.transaction.transaction_service.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.transaction.transaction_service.dto.accountValidateResponseDto;
import com.transaction.transaction_service.dto.accountsValidateDto;

@HttpExchange
public interface accountsClient {
	
	@PostExchange("/account/validate")
	public accountValidateResponseDto validateAccountTransaction(@RequestBody accountsValidateDto accountId);

}
