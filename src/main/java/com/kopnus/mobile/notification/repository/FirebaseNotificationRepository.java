package com.kopnus.mobile.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kopnus.mobile.notification.entity.FirebaseNotificationEntity;

public interface FirebaseNotificationRepository extends JpaRepository<FirebaseNotificationEntity, Integer> {

}
