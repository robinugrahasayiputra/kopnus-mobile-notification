package com.kopnus.mobile.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@Entity
@Table(name = "email_notification")
public class EmailNotificationEntity extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, updatable = false)
	private Integer userId;
	
	@Column(updatable = false)
	private String receiver;

	@Column(nullable = false)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String message;

}
