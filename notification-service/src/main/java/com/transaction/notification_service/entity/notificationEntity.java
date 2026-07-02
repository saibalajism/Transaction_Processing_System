package com.transaction.notification_service.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="notification_details")
public class notificationEntity {
	@Id
	@Column(name="notification_Id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int notificationId;
	
	@Column(name="email_id")
	private String email;
	
	@Column(name="fromAccount")
	private Long fromAccount;
	
	@Column(name="toAccount")
	private Long toAccount;
	
	@Column(name="transactionId")
	private int transactionId;
	
	@Column(name="amount")
	private float amount;
	
	@Column(name="status")
	private String status;
	
	@Column(name="createdDate")
	private Date timeStamp;
	
	@Column(name="customerId")
	private Long customerId;

}
