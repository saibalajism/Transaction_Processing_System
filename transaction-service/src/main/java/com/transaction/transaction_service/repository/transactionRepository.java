package com.transaction.transaction_service.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transaction.transaction_service.entity.transactionCreateEntity;

@Repository
public interface transactionRepository extends JpaRepository<transactionCreateEntity, Integer>{

	Long countByfromAccountAndCreatedDateAfter(Long fromAccount, Date from);
	
	@Query("SELECT COALESCE(AVG(t.amount), 0.0) FROM transactionCreateEntity t WHERE t.fromAccount = :fromAccount AND t.createdDate > :createdDate")
	Double findAverageAmountByfromAccountAndCreatedDate(
	    @Param("fromAccount") Long fromAccount, 
	    @Param("createdDate") Date createdDate
	);

}
