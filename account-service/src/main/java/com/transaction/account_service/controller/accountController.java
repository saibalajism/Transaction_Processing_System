package com.transaction.account_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.account_service.dto.NotificationResponseDto;
import com.transaction.account_service.dto.accountDto;
import com.transaction.account_service.dto.notificationReqDto;
import com.transaction.account_service.dto.responseDto;
import com.transaction.account_service.dto.updateAccountStatusDto;
import com.transaction.account_service.dto.validateDto;
import com.transaction.account_service.entity.accountEntity;
import com.transaction.account_service.service.accountService;
import com.transaction.account_service.service.notificationService;
import com.transaction.account_service.service.validateAccountService;

@RestController
@RequestMapping("/account")
public class accountController {
	
	@Autowired
	private validateAccountService validateService;
	
	@Autowired
	private accountService accService;
	
	@Autowired
	private notificationService notifyService;
	
	
	@PostMapping("/create")
	public accountEntity accCreate(@RequestBody accountDto dto) {
		return accService.createAccount(dto);
	}
	
	@PostMapping("/validate")
	public responseDto validateAccountTransaction(@RequestBody validateDto dto) {
		return validateService.validateAccount(dto);
	}
	
	@PutMapping("/status/update")
	public accountEntity updateAccStatus(@RequestBody updateAccountStatusDto updateDto) {
		return accService.updateAccountStatus(updateDto);
	}
	
	@PostMapping("/notification")
	public NotificationResponseDto notification(@RequestBody notificationReqDto dto) {
		return notifyService.notificationDetails(dto);
	}

}
