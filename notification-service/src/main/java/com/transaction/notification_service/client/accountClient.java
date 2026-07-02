package com.transaction.notification_service.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.transaction.notification_service.dto.accountReqDto;
import com.transaction.notification_service.dto.accountResDto;

@HttpExchange
public interface accountClient {
	
	@PostExchange("/account/notification")
	accountResDto notification(@RequestBody accountReqDto dto);

}
