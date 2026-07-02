package com.transaction.account_service.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.account_service.dto.responseDto;
import com.transaction.account_service.dto.validateDto;
import com.transaction.account_service.entity.accountEntity;
import com.transaction.account_service.repository.accountRepository;

@Service
public class validateAccountService {
	
	private Logger LOGGER = LoggerFactory.getLogger(validateAccountService.class);
	
	@Autowired
	private accountRepository accRepo;

	@SuppressWarnings("null")
	public responseDto validateAccount(validateDto dto) {
		LOGGER.info("input", dto);
		accountEntity validFrom = accRepo.findByaccountNumber(dto.getFromAccount());
		accountEntity validTo = accRepo.findByaccountNumber(dto.getToAccount());
		LOGGER.info("validate From", validFrom);
		LOGGER.info("validate To", validTo);
		if(validFrom == null) {
			responseDto validateFrom = new responseDto();
			validateFrom.setValid("false");
			validateFrom.setMessage("From Account Invalid");
			validateFrom.setAvailableBalance(validFrom.getBalance());
			validateFrom.setTransactionId(dto.getTransactionId());
			validateFrom.setCustomerId(validFrom.getCustomerId());
			LOGGER.info("validate return1", validateFrom);
			return validateFrom;
		}else if(validTo == null) {
			responseDto validateTo = new responseDto();
			validateTo.setValid("false");
			validateTo.setMessage("To Account Invalid");
			validateTo.setAvailableBalance(validTo.getBalance());
			validateTo.setTransactionId(dto.getTransactionId());
			validateTo.setCustomerId(validFrom.getCustomerId());
			LOGGER.info("validate return2", validateTo);
			return validateTo;
		}else if(validFrom.getBalance() < dto.getAmount()) {
			responseDto validateBalance = new responseDto();
			validateBalance.setValid("false");
			validateBalance.setMessage("Insufficient Balance");
			validateBalance.setAvailableBalance(validFrom.getBalance());
			validateBalance.setTransactionId(dto.getTransactionId());
			validateBalance.setCustomerId(validFrom.getCustomerId());
			LOGGER.info("validate return3", validateBalance);
			return validateBalance;
		}else if(validFrom.getAccountStatus().toLowerCase().equals("blocked")) {
			responseDto validateStatus = new responseDto();
			validateStatus.setValid("false");
			validateStatus.setMessage("Account is blocked.");
			validateStatus.setAvailableBalance(validFrom.getBalance());
			validateStatus.setTransactionId(dto.getTransactionId());
			validateStatus.setCustomerId(validFrom.getCustomerId());
			LOGGER.info("validate return4", validateStatus);
			return validateStatus;
		}else {
			accountEntity accDetails = accRepo.findByaccountNumber(dto.getFromAccount());
			accDetails.setBalance(accDetails.getBalance()- dto.getAmount());
			accDetails.setUpdatedDate(new Date());
			accRepo.save(accDetails);
			responseDto validateAcc = new responseDto();
			validateAcc.setValid("true");
			validateAcc.setMessage("Account is valid.");
			validateAcc.setAvailableBalance(validFrom.getBalance());
			validateAcc.setTransactionId(dto.getTransactionId());
			validateAcc.setCustomerId(validFrom.getCustomerId());
			LOGGER.info("validate return5", validateAcc);
			return validateAcc;
		}
	}

}
