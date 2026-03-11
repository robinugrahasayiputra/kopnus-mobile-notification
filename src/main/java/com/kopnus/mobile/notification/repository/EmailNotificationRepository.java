package com.kopnus.mobile.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kopnus.mobile.notification.entity.EmailNotificationEntity;

public interface EmailNotificationRepository extends JpaRepository<EmailNotificationEntity, Integer> {

}
