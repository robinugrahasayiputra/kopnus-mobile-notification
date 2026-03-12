package com.kopnus.mobile.notification.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.dto.NotificationResponse;
import com.kopnus.mobile.notification.service.NotificationProcessorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
/**
 * pada real project, untuk notifikasi tidak akan menggunakan REST seperti ini
 * service terkait yang perlu mengirim notifikasi hanya perlu mengirimkan dari 
 * producer dan kemudian akan di-consume oleh consumer yang ada pada servie notification
 * sehingga proses berjalan paralel
 */
@AllArgsConstructor
@RequestMapping("notification")
@RestController
public class SendNotifController {

	private final NotificationProcessorService processorService;

	@PostMapping("send-bulk")
	public ResponseEntity<NotificationResponse> sendBulk(
			@Valid @RequestBody List<NotificationRequest> notificationRequests) {
		processorService.sendNotificationBulk(notificationRequests);
		return new ResponseEntity<>(
				new NotificationResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null), 
				HttpStatus.OK);
	}
	
	@PostMapping("send-email")
	public ResponseEntity<NotificationResponse> sendEmail(
			@Valid @RequestBody NotificationRequest notificationRequest) {
		processorService.sendNotificationEmail(notificationRequest);
		return new ResponseEntity<>(
				new NotificationResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null), 
				HttpStatus.OK);
	}
	
	@PostMapping("send-sms")
	public ResponseEntity<NotificationResponse> sendSms(
			@Valid @RequestBody NotificationRequest notificationRequest) {
		processorService.sendNotificationSms(notificationRequest);
		return new ResponseEntity<>(
				new NotificationResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null), 
				HttpStatus.OK);
	}
	
	@PostMapping("send-firebase")
	public ResponseEntity<NotificationResponse> sendFirebase(
			@Valid @RequestBody NotificationRequest notificationRequest) {
		processorService.sendNotificationSms(notificationRequest);
		return new ResponseEntity<>(
				new NotificationResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null), 
				HttpStatus.OK);
	}

}
