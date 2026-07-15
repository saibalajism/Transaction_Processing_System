package com.transaction.transaction_service.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.transaction.transaction_service.client.accountsClient;
import com.transaction.transaction_service.dto.accountValidateResponseDto;
import com.transaction.transaction_service.dto.accountsValidateDto;
import com.transaction.transaction_service.dto.fraudRequestDto;
import com.transaction.transaction_service.dto.fraudResponseDto;
import com.transaction.transaction_service.dto.transactionCreateDto;
import com.transaction.transaction_service.entity.transactionCreateEntity;
import com.transaction.transaction_service.repository.transactionRepository;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class transactionCreateService {

	private Logger LOGGER = LoggerFactory.getLogger(transactionCreateService.class);

	@Autowired
	private accountsClient accClient;

	@Autowired
	private transactionRepository transactRepo;

	@Value("${success.kafka.topic-name}")
	private String transactionsucess;

	@Value("${fail.kafka.topic-name}")
	private String transactionfailed;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	  @Autowired
	    @Lazy
	 private static transactionCreateService self;
	 
	@CircuitBreaker(name = "transactionCircuitBreaker",fallbackMethod = "fetchAccountFallback")
	public accountValidateResponseDto getValidatedAccount(accountsValidateDto validateDto) {
		try {
			// 1. Call the http exchange client
			accountValidateResponseDto account = accClient.validateAccountTransaction(validateDto);
			LOGGER.info("account validation response", account);
			// 2. Return the retrieved object directly
			return account;

		} catch (WebClientResponseException.NotFound ex) {
			// Handles 404 from account-service gracefully
			throw new RuntimeException("Account with ID " + validateDto.getFromAccount() + " not found.");
		} catch (WebClientResponseException ex) {
			// Handles 500 or network communication errors
			throw new RuntimeException("Failed to reach account-service: " + ex.getMessage());
		}
	}

	public accountValidateResponseDto fetchAccountFallback(transactionCreateDto dto, CallNotPermittedException Exception) {
		accountValidateResponseDto fail= new accountValidateResponseDto();
		fail.setValid("service_unavailable");
		return fail;
	}
   
	public transactionCreateEntity createTransaction(transactionCreateDto dto) {
		transactionCreateEntity transactEntity = new transactionCreateEntity();
		transactEntity.setTransactionId(10000 + new Random().nextInt(90000));
		transactEntity.setCreatedDate(new Date());
		transactEntity.setAmount(dto.getAmount());
		transactEntity.setCurrency(dto.getCurrency());
		transactEntity.setFromAccount(dto.getFromAccount());
		transactEntity.setToAccount(dto.getToAccount());
		transactEntity.setRemarks((dto.getRemarks()== null || dto.getRemarks().isBlank()) ? "" : dto.getRemarks());

		accountsValidateDto accountDto = new accountsValidateDto();
		accountDto.setAmount(dto.getAmount());
		accountDto.setFromAccount(dto.getFromAccount());
		accountDto.setToAccount(dto.getToAccount());
		accountDto.setTransactionId(transactEntity.getTransactionId());
		try {
			self = this;
			accountValidateResponseDto validateAcc = self.getValidatedAccount(accountDto);
			LOGGER.info("validate entity", validateAcc);
			if (validateAcc == null ) {
				transactEntity.setStatus("Failed due to technical issue1.");
			} else {
				if(validateAcc.getValid().equals("service_unavailable")) {
					transactEntity.setLastUpdatedTime(new Date());
					transactEntity.setStatus("Transaction could not be processed. Please try again after sometime.");
					Map<String, Object> fail = new HashMap<>();
					fail.put("transactionId", transactEntity.getTransactionId());
					fail.put("fromAccount", transactEntity.getFromAccount());
					fail.put("toAccount", transactEntity.getToAccount());
					fail.put("amount", transactEntity.getAmount());
					fail.put("status", "Failed");
					fail.put("timeStamp", transactEntity.getCreatedDate());
					fail.put("customerId", validateAcc.getCustomerId());
					System.out.println(fail);
					kafkaTemplate.send(transactionfailed, Integer.toString(transactEntity.getTransactionId()), fail);
				}
			else if (validateAcc.getValid().equals("true")) {
					transactEntity.setStatus("Proccessed");
					transactEntity.setLastUpdatedTime(new Date());
					Map<String, Object> success = new HashMap<>();
					success.put("transactionId", transactEntity.getTransactionId());
					success.put("fromAccount", transactEntity.getFromAccount());
					success.put("toAccount", transactEntity.getToAccount());
					success.put("amount", Float.valueOf(transactEntity.getAmount()));
					success.put("status", "Success");
					success.put("timeStamp", transactEntity.getCreatedDate());
					success.put("customerId", validateAcc.getCustomerId());
					System.out.println(success);
					kafkaTemplate.send(transactionsucess, Integer.toString(transactEntity.getTransactionId()), success);
				} else if (validateAcc.getValid().equals("false")) {
					if (validateAcc.getMessage().toLowerCase().contains("balance")) {
						transactEntity.setStatus("Insufficient Balance.");
					} else if (validateAcc.getMessage().toLowerCase().contains("to")) {
						transactEntity.setStatus("Invalid credit account.");
					} else if (validateAcc.getMessage().toLowerCase().contains("from")) {
						transactEntity.setStatus("Invalid debit account.");
					} else {
						transactEntity.setStatus("Invalid details.");
					}
					transactEntity.setLastUpdatedTime(new Date());
					Map<String, Object> fail = new HashMap<>();
					fail.put("transactionId", transactEntity.getTransactionId());
					fail.put("fromAccount", transactEntity.getFromAccount());
					fail.put("toAccount", transactEntity.getToAccount());
					fail.put("amount", transactEntity.getAmount());
					fail.put("status", "Failed");
					fail.put("timeStamp", transactEntity.getCreatedDate());
					fail.put("customerId", validateAcc.getCustomerId());
					System.out.println(fail);
					kafkaTemplate.send(transactionfailed, Integer.toString(transactEntity.getTransactionId()), fail);
				} else {

					transactEntity.setLastUpdatedTime(new Date());
					transactEntity.setStatus("Failed");
					Map<String, Object> fail = new HashMap<>();
					fail.put("transactionId", transactEntity.getTransactionId());
					fail.put("fromAccount", transactEntity.getFromAccount());
					fail.put("toAccount", transactEntity.getToAccount());
					fail.put("amount", transactEntity.getAmount());
					fail.put("status", "Success");
					fail.put("timeStamp", transactEntity.getCreatedDate());
					fail.put("customerId", validateAcc.getCustomerId());
					System.out.println(fail);
					kafkaTemplate.send(transactionfailed, Integer.toString(transactEntity.getTransactionId()), fail);
				}
			}
		} catch (Exception e) {

			transactEntity.setLastUpdatedTime(new Date());
			transactEntity.setStatus("Failed due to technical issue2.");
		}
		transactRepo.save(transactEntity);
		return transactEntity;

	}

	
	public fraudResponseDto findFromAccount(fraudRequestDto transactionDetails) {
		// TODO Auto-generated method stub
		System.out.println(transactionDetails);
		Long count = transactRepo.countByfromAccountAndCreatedDateAfter(transactionDetails.getFromAccount(),Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant()));
		float avgAmount = transactRepo.findAverageAmountByfromAccountAndCreatedDate(transactionDetails.getFromAccount(),Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant())).floatValue();
		//Long count = transactRepo.findBytransactionId(transactionId);
		
		fraudResponseDto fraudDetails = new fraudResponseDto();
		fraudDetails.setAvgAmount(avgAmount);
		fraudDetails.setCount(count);
		return fraudDetails;
	}

}
