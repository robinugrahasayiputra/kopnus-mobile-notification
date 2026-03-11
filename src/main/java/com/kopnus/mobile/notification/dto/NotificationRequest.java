package com.kopnus.mobile.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {

	@NotBlank
	private String type;

	@NotNull
	private Integer userId;

	@NotBlank
	private String subject;

	private String message;
	private String receiver;
	private String deviceToken;
}
