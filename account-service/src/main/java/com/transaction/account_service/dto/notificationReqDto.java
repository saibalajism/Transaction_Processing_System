package com.transaction.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class notificationReqDto {
		
		private Long customerId;
		private Long fromAccount;

}
