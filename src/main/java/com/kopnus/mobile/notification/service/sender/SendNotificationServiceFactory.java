package com.kopnus.mobile.notification.service.sender;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.kopnus.mobile.notification.otherenum.NotificationTypeEnum;

/**
 * factory ini dibuat dengan tujuan agar service yang menggunakan interface SendNotificationService
 * dapat mengakses class implementasi terkait, sehingga spring tidak bingung karna ada lebih dari
 * satu bean yang melakukan implementasi
 * 
 * @author rob
 */
@Component
public class SendNotificationServiceFactory {

	private final Map<NotificationTypeEnum, SendNotificationService> serviceMap;

	public SendNotificationServiceFactory(List<SendNotificationService> serviceList) {
		serviceMap = serviceList.stream()
				.collect(Collectors.toMap(SendNotificationService::getType, Function.identity()));
	}

	public SendNotificationService getService(NotificationTypeEnum type) {

		SendNotificationService service = serviceMap.get(type);
		if (service == null) {
			throw new IllegalArgumentException("Unsupported notification type: " + type);
		}
		return service;
	}

}
