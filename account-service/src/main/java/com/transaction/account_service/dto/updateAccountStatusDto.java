package com.transaction.account_service.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class updateAccountStatusDto {
	@NotNull(message = "CustomerId cannot be blank")
	private int customerId;
	
	@NotNull(message = "Account number cannot be blank")
	private int accountNumber;
	
	@NotBlank(message = "Status cannot be blank")
	@Pattern(regexp = "^(active|blocked)$",message = "Status must be either 'active' or 'blocked'" )
	private String status;

}
