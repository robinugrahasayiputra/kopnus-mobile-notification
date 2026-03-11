package com.kopnus.mobile.notification.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@Entity
@Table(name = "firebase_notification")
public class FirebaseNotificationEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, updatable = false)
	private Integer userId;
	
	@Column(updatable = false)
	private String deviceToken;

	@Column(nullable = false)
	private String subject;

	@Column(columnDefinition = "TEXT", nullable = true)
	private String message;

	@Builder.Default
	private boolean read = false;
	
	private LocalDateTime lastUpdatedOn;

}
