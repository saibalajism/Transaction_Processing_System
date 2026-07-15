package com.transaction.account_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class accountDto {
	
	//private Long accountNumber;
	@NotBlank(message = "Account type cannot be blank")
	private String accountType;
	@NotNull(message = "Balance cannot be blank")
	private float balance;
	@NotBlank(message = "Email address is required")
    @Email(message = "Please provide a valid email address format")
	private String email;
	@NotBlank(message="Username cannot be blank")
	private String username;

}
