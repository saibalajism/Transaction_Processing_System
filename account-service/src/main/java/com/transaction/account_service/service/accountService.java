package com.transaction.account_service.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.account_service.dto.accountDto;
import com.transaction.account_service.dto.updateAccountStatusDto;
import com.transaction.account_service.entity.accountEntity;
import com.transaction.account_service.repository.accountRepository;

@Service
public class accountService {

	@Autowired
	private accountRepository accRepo;

	public accountEntity createAccount(accountDto dto) {
		// TODO Auto-generated method stub
		accountEntity accCreate = new accountEntity();
		accCreate.setCustomerId(100 + new Random().nextInt(9000));
		accCreate.setAccountNumber(10 + new Random().nextInt(90000));

		accCreate.setUpdatedDate(new Date());
		accCreate.setAccountType(dto.getAccountType());
		accCreate.setBalance(dto.getBalance());
		accCreate.setUsername(dto.getUsername());
		if (dto.getEmail() == null) {
			accCreate.setAccountStatus("Account cannot be created. Please enter an email id.");
			accCreate.setEmailId("");
		} else if (dto.getEmail().isEmpty()) {
			accCreate.setAccountStatus("Account cannot be created. Please enter valid email.");
			accCreate.setEmailId("");
		} else if (accRepo.existsByemailId(dto.getEmail())) {
			accCreate.setAccountStatus("Account cannot be created. Email id already exsists.");
			accCreate.setEmailId("");
		} else {
			accCreate.setEmailId(dto.getEmail());
			accRepo.save(accCreate);
		}
		System.out.println(accCreate);
		return accCreate;
	}

	public accountEntity updateAccountStatus(updateAccountStatusDto updateDto) {
		// TODO Auto-generated method stub
		accountEntity acc = accRepo.findBycustomerId(updateDto.getCustomerId());
		if (acc.equals(null)) {
			throw new RuntimeException("Cannot find Customer with ID: " + updateDto.getCustomerId());
		} else {
			acc.setAccountStatus(updateDto.getStatus());
		}
		accRepo.save(acc);
		return null;
	}

}
