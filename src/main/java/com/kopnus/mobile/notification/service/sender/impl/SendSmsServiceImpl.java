package com.kopnus.mobile.notification.service.sender.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.entity.SmsNotificationEntity;
import com.kopnus.mobile.notification.otherenum.NotificationTypeEnum;
import com.kopnus.mobile.notification.repository.SmsNotificationRepository;
import com.kopnus.mobile.notification.service.sender.SendNotificationService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class SendSmsServiceImpl implements SendNotificationService {
	
	private final SmsNotificationRepository smsNotificationRepository;

	@Override
	public NotificationTypeEnum getType() {
		return NotificationTypeEnum.SMS;
	}
	
	@Transactional
	@Override
	public void send(NotificationRequest notificationRequest) {
		
		/**
		 * pengecekan valid dilakukan disini agar data yang tidak valid tetap dapat disimpan untuk 
		 * kebutuhan tracking dan audit
		 */
		boolean valid = notificationRequest.getReceiver() != null && !notificationRequest.getReceiver().isEmpty();

		if (valid) {
			/**
			 * code implementasi untuk mengirim notif akan ditambahkan disini sesuai
			 * kebutuhan
			 */
		}
		
		saveMessage(notificationRequest, valid);
	}
	
	private void saveMessage(NotificationRequest notificationRequest, boolean valid) {
		SmsNotificationEntity entity = SmsNotificationEntity.builder()
				.message(notificationRequest.getMessage())
				.receiver(notificationRequest.getReceiver())
				.subject(notificationRequest.getSubject())
				.userId(notificationRequest.getUserId())
				.createdBy("SMS SERVICE")
				.createdOn(LocalDateTime.now())
				.valid(valid)
				.build();
		smsNotificationRepository.save(entity);
	}

}
