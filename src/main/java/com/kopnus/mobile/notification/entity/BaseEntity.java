package com.kopnus.mobile.notification.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@Setter
@Getter
public class BaseEntity {
	
	@Builder.Default
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@Column(nullable = false, updatable = false)
	private String createdBy;
	
	@Builder.Default
	private boolean valid = true;

}
