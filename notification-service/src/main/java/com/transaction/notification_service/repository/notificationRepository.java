package com.transaction.notification_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.notification_service.entity.notificationEntity;

@Repository
public interface notificationRepository extends JpaRepository<notificationEntity, Integer> {

}
