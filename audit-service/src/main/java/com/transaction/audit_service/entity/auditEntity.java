package com.transaction.audit_service.entity;

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
@Table(name="audit_details")
public class auditEntity {
	
	@Id
	@Column(name="auditId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	@Column(name="transactionId")
	private int transactionId;
	@Column(name="action")
	private String action;
	@Column(name="amount")
	private float amount;
	@Column(name="status")
	private String status;
	@Column(name="createdDate")
	private Date timestamp;

}
