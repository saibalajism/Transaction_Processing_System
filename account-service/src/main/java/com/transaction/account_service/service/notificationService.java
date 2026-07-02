package com.transaction.account_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.account_service.dto.NotificationResponseDto;
import com.transaction.account_service.dto.notificationReqDto;
import com.transaction.account_service.entity.accountEntity;
import com.transaction.account_service.repository.accountRepository;

@Service
public class notificationService {
	
	@Autowired
	private accountRepository accRepo;

	public NotificationResponseDto notificationDetails(notificationReqDto dto) {
		// TODO Auto-generated method stub
		accountEntity acc = accRepo.findByaccountNumber(dto.getFromAccount());
		NotificationResponseDto res =new NotificationResponseDto();
		res.setAccNumber(acc.getAccountNumber());
		res.setCustomerId(acc.getCustomerId());
		res.setEmail(acc.getEmailId());
		return res;
	}

}
