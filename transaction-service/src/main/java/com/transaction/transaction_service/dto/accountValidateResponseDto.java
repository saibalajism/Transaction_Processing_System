package com.transaction.transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class accountValidateResponseDto {
	private int transactionId;
	private String valid;
	private float availableBalance;
	private String message;
	private int customerId;

}
