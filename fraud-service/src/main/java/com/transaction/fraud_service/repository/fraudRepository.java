package com.transaction.fraud_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.fraud_service.entity.fraudEntity;

@Repository
public interface fraudRepository extends JpaRepository<fraudEntity, Integer> {

}
