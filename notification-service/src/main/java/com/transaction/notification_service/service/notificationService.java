package com.transaction.notification_service.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.transaction.notification_service.client.accountClient;
import com.transaction.notification_service.dto.accountReqDto;
import com.transaction.notification_service.dto.accountResDto;
import com.transaction.notification_service.entity.notificationEntity;
import com.transaction.notification_service.repository.notificationRepository;

@Service
public class notificationService {
	@Autowired
	private notificationRepository repo;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private accountClient accClient;
	
	@KafkaListener(topics="createTransactionSuccess", groupId="transact_success")
	public void transactionSuccess(Map<String, Object> transactDetails) {
		notificationEntity success = new notificationEntity();
		success.setAmount(((Number) transactDetails.get("amount")).floatValue());
		success.setTransactionId((int)transactDetails.get("transactionId"));
		success.setTimeStamp(new Date(((Number) transactDetails.get("timeStamp")).longValue()));
		success.setStatus((String) transactDetails.get("status"));
		success.setCustomerId(((Number)transactDetails.get("customerId")).longValue());
		success.setFromAccount(((Number)transactDetails.get("fromAccount")).longValue());
		success.setToAccount(((Number)transactDetails.get("toAccount")).longValue());
		accountReqDto accReq = new accountReqDto();
		accReq.setCustomerId(success.getCustomerId());
		accReq.setFromAccount(success.getFromAccount());
		
		accountResDto accountDetails = accClient.notification(accReq);
		success.setEmail(accountDetails.getEmail());
		this.sendMail(accountDetails.getEmail(),success.getCustomerId(),success.getFromAccount(), success.getTransactionId(), "success");
		System.out.println(success);
		repo.save(success);
	}
	
	@KafkaListener(topics="createTransactionFailed", groupId="transact_success")
	public void transactionFailed(Map<String, Object> transactDetails) {
		notificationEntity fail = new notificationEntity();
		fail.setAmount(((Number) transactDetails.get("amount")).floatValue());
		fail.setTransactionId((int)transactDetails.get("transactionId"));
		fail.setTimeStamp(new Date(((Number) transactDetails.get("timeStamp")).longValue()));
		fail.setStatus((String) transactDetails.get("status"));
		fail.setCustomerId((Long)transactDetails.get("customerId"));
		fail.setFromAccount((Long)transactDetails.get("fromAccount"));
		fail.setToAccount((Long)transactDetails.get("toAccount"));
		accountReqDto accReq = new accountReqDto();
		accReq.setCustomerId(fail.getCustomerId());
		accReq.setFromAccount(fail.getFromAccount());
		
		accountResDto accountDetails = accClient.notification(accReq);
		fail.setEmail(accountDetails.getEmail());
		this.sendMail(accountDetails.getEmail(),fail.getCustomerId(),fail.getFromAccount(), fail.getTransactionId(), "failed");
		System.out.println(fail);
		repo.save(fail);
	}

	private void sendMail(String email, Long customerId, Long fromAccount, int transactionId, String Status) {
		// TODO Auto-generated method stub
	    try {
	        org.springframework.mail.SimpleMailMessage message = new org.springframework.mail.SimpleMailMessage();
	        
	        // ✨ THE FIX: Dynamically maps the recipient address to the 'email' argument variable 
	        message.setTo(email); 
	        
	        // 🔒 HARDCODED SENDER: Explicitly sets your authenticated username identity
	        //message.setFrom("smsaibalaji@gmail.com"); 
	        
	        message.setSubject("Transaction Alert: " + fromAccount);
	        
	        String emailBody = String.format(
	            "Dear %d,\n\n" +
	            "This is an automated email.\n" +
	            "Your transaction was %s with below details: \n\n" +
	            "■ Customer Profile ID: %d\n" +
	            "■ Account Number : %d\n\n" +
	            "■ Transaction Id: %d\n\n" +
	            "Best Regards,\n" +
	            "Banking Team",
	            customerId, Status, customerId, fromAccount, transactionId
	        );
	        message.setText(emailBody);
	        mailSender.send(message);
	        System.out.println("Customer alert successfully sent to: " + email);

	    } catch (Exception ex) {
	        System.err.println("Could not complete direct customer mail notification delivery: " + ex.getMessage());
	    }
		
	}

}
