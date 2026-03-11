package com.kopnus.mobile.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kopnus.mobile.notification.entity.SmsNotificationEntity;

public interface SmsNotificationRepository extends JpaRepository<SmsNotificationEntity, Integer> {

}
