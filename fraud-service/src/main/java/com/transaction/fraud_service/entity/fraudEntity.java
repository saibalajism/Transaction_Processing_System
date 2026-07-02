package com.transaction.fraud_service.entity;

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
@Table(name="fraud_details")
public class fraudEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fraudId")
	private int fraudId;
	@Column(name="transactionId")
	private int transactionId;
	@Column(name="rule_triggered")
	private String rule;
	@Column(name="reason")
	private String Reason;

}
