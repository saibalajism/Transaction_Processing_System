package com.transaction.transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class fraudResponseDto {
	private Long count;
	private float AvgAmount;

}
