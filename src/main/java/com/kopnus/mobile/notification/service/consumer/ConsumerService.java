package com.kopnus.mobile.notification.service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kopnus.mobile.notification.constant.KafkaTopicConstant;
import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.otherenum.NotificationTypeEnum;
import com.kopnus.mobile.notification.service.sender.SendNotificationService;
import com.kopnus.mobile.notification.service.sender.SendNotificationServiceFactory;

import lombok.AllArgsConstructor;
import tools.jackson.databind.ObjectMapper;
/**
 * class ini bertujuan untuk menangkap semua message yang dikirimkan oleh producer berdasarkan topik
 * penggunaan SendNotificationServiceFactory bertujuan untuk memudahkan jika dikemudian hari 
 * akan menambahkan implementasi notifikasi baru dengan channel yang berbeda
 * penambahan tidak akan mengubah atau menyenggol channel existing
 * 
 * @author rob
 */
@AllArgsConstructor
@Service
public class ConsumerService {
	
	private final SendNotificationServiceFactory factory;
	private final ObjectMapper mapper = new ObjectMapper();

	@KafkaListener(topics = KafkaTopicConstant.EMAIL_NOTIFICATION_TOPIC)
	void consumeEmail(String message) {
		SendNotificationService service = factory.getService(NotificationTypeEnum.EMAIL);
		service.send(mapper.readValue(message, NotificationRequest.class));
	}
	
	@KafkaListener(topics = KafkaTopicConstant.SMS_NOTIFICATION_TOPIC)
	void consumeSms(String message) {
		SendNotificationService service = factory.getService(NotificationTypeEnum.SMS);
		service.send(mapper.readValue(message, NotificationRequest.class));
	}
	
	@KafkaListener(topics = KafkaTopicConstant.FIREBASE_NOTIFICATION_TOPIC)
	void consumeFireBase(String message) {
		SendNotificationService service = factory.getService(NotificationTypeEnum.FIREBASE);
		service.send(mapper.readValue(message, NotificationRequest.class));
	}

}
