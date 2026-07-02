package com.transaction.audit_service.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.transaction.audit_service.entity.auditEntity;
import com.transaction.audit_service.repository.auditRepository;

@Service
public class auditService {
	
	@Autowired
	private auditRepository auditRepo;
	
	@KafkaListener(topics="createTransactionSuccess", groupId="transact_success")
	public void transactionSuccess(Map<String, Object> transactDetails) {
		auditEntity audit = new auditEntity();
		audit.setAmount(((Number) transactDetails.get("amount")).floatValue());
		audit.setTransactionId((int)transactDetails.get("transactionId"));
		audit.setTimestamp(new Date(((Number) transactDetails.get("timeStamp")).longValue()));
		audit.setStatus((String) transactDetails.get("status"));
		audit.setAction("Transaction_Completed");
		System.out.println(audit);
		auditRepo.save(audit);
	}
	
	@KafkaListener(topics="createTransactionFailed", groupId="transact_success")
	public void transactionFailed(Map<String, Object> transactDetails) {
		auditEntity audit = new auditEntity();
		audit.setAmount(((Number) transactDetails.get("amount")).floatValue());
		audit.setTransactionId((int)transactDetails.get("transactionId"));
		audit.setTimestamp((Date) transactDetails.get("timeStamp"));
		audit.setStatus((String) transactDetails.get("status"));
		audit.setAction("Transaction_Failed");
		auditRepo.save(audit);
	}

	public auditEntity fetchAuditDetails(int transactionId) {
		auditEntity details = auditRepo.findBytransactionId(transactionId);
		return details;
	}

}
