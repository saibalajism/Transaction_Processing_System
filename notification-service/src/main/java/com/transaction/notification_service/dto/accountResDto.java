package com.transaction.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class accountResDto {
	
	private String email;
	private int customerId;
	private int accountNumber;

}
