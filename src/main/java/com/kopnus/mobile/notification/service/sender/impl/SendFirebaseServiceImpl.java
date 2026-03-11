package com.kopnus.mobile.notification.service.sender.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.entity.FirebaseNotificationEntity;
import com.kopnus.mobile.notification.otherenum.NotificationTypeEnum;
import com.kopnus.mobile.notification.repository.FirebaseNotificationRepository;
import com.kopnus.mobile.notification.service.sender.SendNotificationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SendFirebaseServiceImpl implements SendNotificationService {
	
	private final FirebaseNotificationRepository firebaseNotificationRepository;

	@Override
	public NotificationTypeEnum getType() {
		return NotificationTypeEnum.FIREBASE;
	}
	
	@Transactional
	@Override
	public void send(NotificationRequest notificationRequest) {
		
		/**
		 * pengecekan valid dilakukan disini agar data yang tidak valid tetap dapat disimpan untuk 
		 * kebutuhan tracking dan audit
		 */
		boolean valid = notificationRequest.getDeviceToken() != null && !notificationRequest.getDeviceToken().isEmpty();

		if (valid) {
			/**
			 * code implementasi untuk mengirim notif akan ditambahkan disini sesuai
			 * kebutuhan
			 */
		}
		
		saveMessage(notificationRequest, valid);
	}
	
	public void saveMessage(NotificationRequest notificationRequest, boolean valid) {
		FirebaseNotificationEntity entity = FirebaseNotificationEntity.builder()
				.deviceToken(notificationRequest.getDeviceToken())
				.message(notificationRequest.getMessage())
				.subject(notificationRequest.getSubject())
				.userId(notificationRequest.getUserId())
				.createdBy("FIREBASE SERVICE")
				.createdOn(LocalDateTime.now())
				.valid(valid)
				.build();
		firebaseNotificationRepository.save(entity);
	}

}
