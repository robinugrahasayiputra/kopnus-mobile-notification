package com.kopnus.mobile.notification.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.dto.NotificationResponse;
import com.kopnus.mobile.notification.service.NotificationProcessorService;
import com.kopnus.mobile.notification.service.producer.ProducerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RequestMapping("notification")
@RestController
public class SendNotifController {

	private final NotificationProcessorService processorService;

	@PostMapping("send")
	public ResponseEntity<NotificationResponse> postMethodName(
			@Valid @RequestBody List<NotificationRequest> notificationRequest) {
		processorService.sendNotificationBulk(notificationRequest);
		return new ResponseEntity<>(
				new NotificationResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null), 
				HttpStatus.OK);
	}

}
