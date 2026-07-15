package com.transaction.account_service.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="account_details")
public class accountEntity {
	
	@Id
	@Column(name="customer_id")
	private int customerId;
	

	@Column(name="account_number")
	private int accountNumber;
	
	@Column(name="account_type")
	private String accountType;
	
	@Column(name="balance")
	private float balance;
	
	@Column(name="account_status")
	private String accountStatus;
	
	@Column(name="updated_time")
	private Date updatedDate;
	
	@Column(name="mailId")
	private String emailId;
	
	@Column(name="username")
	private String username;

}
