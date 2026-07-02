package com.transaction.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class accountReqDto {
	
	private Long customerId;
	private Long fromAccount;
	
}
