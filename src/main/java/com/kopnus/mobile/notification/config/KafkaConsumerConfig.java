package com.kopnus.mobile.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;

@Configuration
public class KafkaConsumerConfig {

	/**
	 * bean ini agar kafka consumer dapat melakukan retry jika terjadi error
	 * dengan retry sebanyak 5 kali dengan maksimal inteval 30 detik
	 * @param kafkaTemplate
	 * @return
	 */
	@Bean
	DefaultErrorHandler errorHandler(KafkaTemplate<String, String> kafkaTemplate) {

		DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
		ExponentialBackOffWithMaxRetries backOff = new ExponentialBackOffWithMaxRetries(5);

		backOff.setInitialInterval(2000);
		backOff.setMultiplier(2);
		backOff.setMaxInterval(30000);
		return new DefaultErrorHandler(recoverer, backOff);
	}

}
