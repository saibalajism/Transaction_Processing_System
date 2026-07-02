package com.transaction.transaction_service.entity;

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
@Table(name="transaction_details")
public class transactionCreateEntity {
	@Id
	@Column(name="transaction_id")
	 private int transactionId;
	
	@Column(name="from_account")
	 private Long fromAccount;
	
	@Column(name="customer_id")
	private int customerId;
	
	@Column(name="to_account")
	 private Long toAccount;
	
	@Column(name="amount")
	 private float amount;
	
	@Column(name="currency")
	 private String currency;
	
	@Column(name="remarks")
	 private String remarks;
	
	@Column(name="created_date")
	 private Date createdDate;
	
	@Column(name="status")
	private String Status;
	
	@Column(name="updated_time")
	private Date LastUpdatedTime;

}
