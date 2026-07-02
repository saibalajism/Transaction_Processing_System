package com.transaction.audit_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.audit_service.entity.auditEntity;

@Repository
public interface auditRepository extends JpaRepository<auditEntity, Integer>{

	auditEntity findBytransactionId(int transactionId);

}
