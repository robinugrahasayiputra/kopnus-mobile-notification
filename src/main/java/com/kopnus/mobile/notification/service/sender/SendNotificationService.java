package com.kopnus.mobile.notification.service.sender;

import com.kopnus.mobile.notification.dto.NotificationRequest;
import com.kopnus.mobile.notification.otherenum.NotificationTypeEnum;

public interface SendNotificationService {
	
	NotificationTypeEnum getType();
	void send(NotificationRequest notificationRequest);
}
