package com.kopnus.mobile.notification.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.service.producer.ProducerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@AllArgsConstructor
@Service
public class NotificationProcessorService {

	private final ProducerService producerService;
	private final ObjectMapper mapper = new ObjectMapper();

	public void sendNotificationBulk(List<NotificationRequest> notificationRequests) {

		/**
		 * dengan menggunakan kafka, setiap notifikasi akan dikerjakan secara paralel
		 * sehingga performance aplikasi akan lebih cepat daripada jika dilakukan secara
		 * berurut
		 */
		for (var notificationRequest : notificationRequests) {

			switch (notificationRequest.getType()) {
			case "EMAIL": {
				sendNotificationEmail(notificationRequest);
				break;
			}
			case "SMS": {
				sendNotificationSms(notificationRequest);
				break;
			}
			case "FIREBASE": {
				sendNotificationFirebase(notificationRequest);
				break;
			}
			default:
				log.error("Unexpected Notification Type: " + notificationRequest.getType());
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
			}
		}

	}

	public void sendNotificationEmail(NotificationRequest notificationRequest) {
		String payload = mapper.writeValueAsString(notificationRequest);
		producerService.sendEmailNotif(payload);
	}

	public void sendNotificationSms(NotificationRequest notificationRequest) {
		String payload = mapper.writeValueAsString(notificationRequest);
		producerService.sendSmsNotif(payload);
	}

	public void sendNotificationFirebase(NotificationRequest notificationRequest) {
		String payload = mapper.writeValueAsString(notificationRequest);
		producerService.sendFirebaseNotif(payload);
	}

}
