package com.kopnus.mobile.notification.service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kopnus.mobile.notification.constant.KafkaTopicConstant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * class producer ini normalnya berada pada service terkait yang ingin men-trigger notification
 * misalkan sistem akan memberikan notifikasi transaksi berhasil, 
 * maka service transaksi yang akan mengirimkan message-nya ke kafka
 * 
 * @author rob
 */
@Slf4j
@AllArgsConstructor
@Service
public class ProducerService {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendEmailNotif(String message) {
		log.info("send email : {}", message);
		kafkaTemplate.send(KafkaTopicConstant.EMAIL_NOTIFICATION_TOPIC, message);
	}
	
	public void sendSmsNotif(String message) {
		log.info("send sms : {}", message);
		kafkaTemplate.send(KafkaTopicConstant.SMS_NOTIFICATION_TOPIC, message);
	}
	
	public void sendFirebaseNotif(String message) {
		log.info("send firebase : {}", message);
		kafkaTemplate.send(KafkaTopicConstant.FIREBASE_NOTIFICATION_TOPIC, message);
	}

}
