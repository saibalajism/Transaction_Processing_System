package com.transaction.audit_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.audit_service.entity.auditEntity;
import com.transaction.audit_service.service.auditService;

@RestController
@RequestMapping("/audit")
public class auditController {
	
	@Autowired
	private auditService service;
	
	@GetMapping("/{transactionId}")
	public auditEntity getAuditById(@PathVariable("transactionId") int transactionId ) {
		return service.fetchAuditDetails(transactionId);
	}

}
