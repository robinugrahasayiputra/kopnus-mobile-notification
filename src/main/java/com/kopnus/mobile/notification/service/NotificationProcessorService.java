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

	public void sendNotificationBulk(List<NotificationRequest> notificationRequests) {
		ObjectMapper mapper = new ObjectMapper();

		/**
		 * dengan menggunakan kafka, setiap notifikasi akan dikerjakan secara paralel
		 * sehingga performance aplikasi akan lebih cepat daripada jika dilakukan secara berurut
		 */
		for (var notificationRequest : notificationRequests) {
			switch (notificationRequest.getType()) {
			case "EMAIL": {
				producerService.sendEmailNotif(mapper.writeValueAsString(notificationRequest));
				break;
			}
			case "SMS": {
				producerService.sendSmsNotif(mapper.writeValueAsString(notificationRequest));
				break;
			}
			case "FIREBASE": {
				producerService.sendFirebaseNotif(mapper.writeValueAsString(notificationRequest));
				break;
			}
			default:
				log.error("Unexpected Notification Type: " + notificationRequest.getType());
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
			}
		}

	}

}
