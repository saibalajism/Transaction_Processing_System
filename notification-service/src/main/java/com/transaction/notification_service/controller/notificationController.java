package com.transaction.notification_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.notification_service.entity.notificationEntity;
import com.transaction.notification_service.service.notificationService;

@RestController
@RequestMapping("/notification")
public class notificationController {
	
	@Autowired
	private notificationService service;
	
	@GetMapping("/{id}")
	public notificationEntity findNotification(@PathVariable int id) {
		return service.findByNotifyId(id);
	}

}
