package com.kopnus.mobile.notification.service.sender.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.entity.EmailNotificationEntity;
import com.kopnus.mobile.notification.otherenum.NotificationTypeEnum;
import com.kopnus.mobile.notification.repository.EmailNotificationRepository;
import com.kopnus.mobile.notification.service.sender.SendNotificationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SendEmailServiceImpl implements SendNotificationService {

	private final EmailNotificationRepository emailNotificationRepository;

	@Override
	public NotificationTypeEnum getType() {
		return NotificationTypeEnum.EMAIL;
	}

	@Transactional
	@Override
	public void send(NotificationRequest notificationRequest) {

		/**
		 * pengecekan valid dilakukan disini agar data yang tidak valid tetap dapat disimpan untuk 
		 * kebutuhan tracking dan audit
		 */
		boolean valid =  StringUtils.hasText(notificationRequest.getReceiver());

		if (valid) {
			/**
			 * code implementasi untuk mengirim notif akan ditambahkan disini sesuai
			 * kebutuhan
			 */
		}
		saveMessage(notificationRequest, valid);
	}

	private void saveMessage(NotificationRequest notificationRequest, boolean valid) {

		EmailNotificationEntity entity = EmailNotificationEntity.builder()
				.message(notificationRequest.getMessage())
				.receiver(notificationRequest.getReceiver())
				.subject(notificationRequest.getSubject())
				.userId(notificationRequest.getUserId())
				.createdBy("EMAIL SERVICE")
				.valid(valid)
				.build();
		emailNotificationRepository.save(entity);
	}

}
