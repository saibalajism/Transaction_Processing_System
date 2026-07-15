package com.transaction.account_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.account_service.entity.accountEntity;

@Repository
public interface accountRepository extends JpaRepository<accountEntity, Integer> {

	accountEntity findByaccountNumber(int fromAccount);

	accountEntity findBycustomerId(int customerId);

	accountEntity findByaccountNumber(Long fromAccount);

	boolean existsByemailId(String email);

}
