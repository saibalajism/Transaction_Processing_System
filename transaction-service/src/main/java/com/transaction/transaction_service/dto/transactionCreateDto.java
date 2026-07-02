package com.transaction.transaction_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class transactionCreateDto {
	
	 @NotNull(message = "Debit Account number is mandatory")
	 private Long fromAccount;
	 @NotNull(message = "Credit Account number is mandatory")
	 private Long toAccount;
	 @NotNull(message = "Debit Amount is mandatory")
	 private float amount;
	 @NotNull(message = "Debit Currency is mandatory")
	 private String currency;
	 
	 private String remarks="";
	 

}
