package com.transaction.fraud_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class transactionResponseDto {
	
	private float AvgAmount;
	private Long count;

}
