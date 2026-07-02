package com.transaction.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class validateDto {
	
	private int transactionId;
	private int fromAccount;
	private int toAccount;
	private float amount;

}
