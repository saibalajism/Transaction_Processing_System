package com.transaction.fraud_service.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.transaction.fraud_service.client.transactionCreateClient;
import com.transaction.fraud_service.dto.transactionDetailsRequestDto;
import com.transaction.fraud_service.dto.transactionResponseDto;
import com.transaction.fraud_service.entity.fraudEntity;
import com.transaction.fraud_service.repository.fraudRepository;


@Service
public class fraudService {
	
	private transactionDetailsRequestDto transactDetails;
	
	//private static transactionResponseDto transactResDto;
	
	@Autowired
	private fraudRepository fraudRepo;
	
	@Value("${fraud.count}")
	private Long fraudCount;
	
	@Value("${fraud.thresholdAmount}")
	private float fraudThresholdAmount;
	
	@Autowired
	private transactionCreateClient transactClient;
	
	
	@KafkaListener(topics="createTransactionSuccess", groupId="fraud-service")
	public void fraudCheckTransactionSuccess(Map<String, Object> transactDetails) {
		transactionDetailsRequestDto details = new transactionDetailsRequestDto();
		details.setAmount(((Number) transactDetails.get("amount")).floatValue());
		details.setCreatedDate(new Date(((Number) transactDetails.get("timeStamp")).longValue()));
		details.setTransactionId((int) transactDetails.get("transactionId"));
		details.setFromAccount(((Number)transactDetails.get("fromAccount")).longValue());
		System.out.println(details);
		transactionResponseDto res = this.getTransactionDetails(details);
		this.fraudCheck(details, res);
	}
	
	public transactionResponseDto getTransactionDetails(transactionDetailsRequestDto data) {
		try {
			// 1. Call the http exchange client
			transactionResponseDto account = transactClient.findByfromAccount(data);
			System.out.println(account);
			transactionResponseDto transactResDto = new transactionResponseDto();
			transactResDto.setAvgAmount((float)account.getAvgAmount());
			transactResDto.setCount(((Number)account.getCount()).longValue());
			return transactResDto;

		} catch (WebClientResponseException.NotFound ex) {
			// Handles 404 from transact-service gracefully
			
			throw new RuntimeException("The" + data.getFromAccount() + " not found.");
		} catch (WebClientResponseException ex) {
			// Handles 500 or network communication errors
			throw new RuntimeException("Failed to reach transaction-service: " + ex);
		}
	}

	private void fraudCheck(transactionDetailsRequestDto details, transactionResponseDto resDto) {
		// TODO Auto-generated method stub
		fraudEntity fraudData= new fraudEntity();
		if(resDto.getCount()>fraudCount){
			fraudData.setTransactionId(details.getTransactionId());
			fraudData.setRule("HIGH_FREQUENCY");
			fraudData.setReason("More than" + fraudCount +" within short span of time");
			System.out.println(fraudData);
			fraudRepo.save(fraudData);
		}else if(resDto.getAvgAmount()>fraudThresholdAmount*10) {
			fraudData.setTransactionId(details.getTransactionId());
			fraudData.setRule("HIGH_AMOUNT");
			fraudData.setReason("Suspicious transaction with amount " + fraudThresholdAmount*10);
			System.out.println(fraudData);
			fraudRepo.save(fraudData);		
		}
	}

	public fraudEntity findFraud(int id) {
		// TODO Auto-generated method stub
		fraudEntity fraudDetails = fraudRepo.findById(id).orElseThrow(() -> new RuntimeException("Transaction data not found for ID: " + id));
		return fraudDetails;
	}
}
