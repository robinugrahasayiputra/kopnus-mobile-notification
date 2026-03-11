package com.kopnus.mobile.notification.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.service.producer.ProducerService;

@ExtendWith(MockitoExtension.class)
public class NotificationProcessorServiceTest {

	@Mock
	private ProducerService producerService;

	@InjectMocks
	private NotificationProcessorService service;

	private NotificationRequest emailRequest;
	private NotificationRequest smsRequest;
	private NotificationRequest firebaseRequest;

	@BeforeEach
	void setUp() {
		emailRequest = new NotificationRequest();
		emailRequest.setType("EMAIL");

		smsRequest = new NotificationRequest();
		smsRequest.setType("SMS");

		firebaseRequest = new NotificationRequest();
		firebaseRequest.setType("FIREBASE");
	}

	@Test
	void emailNotificationTest() {
		service.sendNotificationBulk(List.of(emailRequest));

		verify(producerService, times(1)).sendEmailNotif(anyString());
		verify(producerService, never()).sendSmsNotif(anyString());
		verify(producerService, never()).sendFirebaseNotif(anyString());
	}

	@Test
	void smsNotificationTest() {
		service.sendNotificationBulk(List.of(smsRequest));

		verify(producerService, times(1)).sendSmsNotif(anyString());
		verify(producerService, never()).sendEmailNotif(anyString());
		verify(producerService, never()).sendFirebaseNotif(anyString());
	}

	@Test
	void firebaseNotificationTest() {
		service.sendNotificationBulk(List.of(firebaseRequest));

		verify(producerService, times(1)).sendFirebaseNotif(anyString());
		verify(producerService, never()).sendEmailNotif(anyString());
		verify(producerService, never()).sendSmsNotif(anyString());
	}

	@Test
	void multipleNotificationsTest() {
		service.sendNotificationBulk(List.of(emailRequest, smsRequest, firebaseRequest));

		verify(producerService).sendEmailNotif(anyString());
		verify(producerService).sendSmsNotif(anyString());
		verify(producerService).sendFirebaseNotif(anyString());
	}

	@Test
	void throwExceptionForUnknownTypeTest() {
		NotificationRequest unknown = new NotificationRequest();
		unknown.setType("WHATSAPP");

		assertThrows(ResponseStatusException.class, () -> service.sendNotificationBulk(List.of(unknown)));
		verifyNoInteractions(producerService);
	}

	@Test
	void doNothingWhenListEmptyTest() {
		service.sendNotificationBulk(List.of());
		verifyNoInteractions(producerService);
	}

}
