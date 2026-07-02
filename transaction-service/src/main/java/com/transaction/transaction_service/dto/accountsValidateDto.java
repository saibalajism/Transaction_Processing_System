package com.transaction.transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class accountsValidateDto {
	
	private int transactionId;
	private Long fromAccount;
	private Long toAccount;
	private float amount;

}
