package com.transaction.fraud_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.fraud_service.entity.fraudEntity;
import com.transaction.fraud_service.service.fraudService;

@RestController
@RequestMapping("/fraud")
public class fraudController {
	
	@Autowired
	private fraudService service;
	
	@GetMapping("/{Id}")
	public fraudEntity findFraudById(@PathVariable int Id) {
		return service.findFraud(Id);
	}

}
