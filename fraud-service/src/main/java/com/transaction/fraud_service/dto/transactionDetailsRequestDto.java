package com.transaction.fraud_service.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class transactionDetailsRequestDto {
	 private int transactionId;
	 private Long FromAccount;
	 private float amount;
	 private Date createdDate;

}
