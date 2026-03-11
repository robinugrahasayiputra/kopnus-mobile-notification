//package com.kopnus.mobile.notification.exception;
//
//import java.util.stream.Collectors;
//
//import org.apache.kafka.common.errors.ResourceNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import com.kopnus.mobile.notification.dto.NotificationResponse;
//
//import jakarta.validation.ConstraintViolationException;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity<NotificationResponse> handleResourceNotFound(ResourceNotFoundException ex) {
//		NotificationResponse response = new NotificationResponse(HttpStatus.NOT_FOUND.value(),
//				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
//		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//	}
//
//	@ExceptionHandler(IllegalArgumentException.class)
//	public ResponseEntity<NotificationResponse> handleIllegalArgument(IllegalArgumentException ex) {
//		NotificationResponse response = new NotificationResponse(HttpStatus.NOT_ACCEPTABLE.value(),
//				HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), ex.getMessage());
//		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//	}
//
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<NotificationResponse> handleConstraintViolation(ConstraintViolationException ex) {
//		String errorMsg = ex.getConstraintViolations().stream()
//				.map(v -> v.getPropertyPath() + ": " + v.getMessage())
//				.collect(Collectors.joining(", "));
//		NotificationResponse response = new NotificationResponse(HttpStatus.BAD_REQUEST.value(),
//				HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMsg);
//		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//	}
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<NotificationResponse> handleMethodArgumentNotValid(
//	        MethodArgumentNotValidException ex) {
//
//	    String errorMsg = ex.getBindingResult()
//	            .getFieldErrors()
//	            .stream()
//	            .map(err -> err.getField() + ": " + err.getDefaultMessage())
//	            .collect(Collectors.joining(", "));
//
//	    NotificationResponse response = new NotificationResponse(
//	            HttpStatus.BAD_REQUEST.value(),
//	            HttpStatus.BAD_REQUEST.getReasonPhrase(),
//	            errorMsg
//	    );
//
//	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//	}
//	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<NotificationResponse> handleGeneric(Exception ex){
//		log.error("<<ERROR>> Exception :: ", ex);
//		NotificationResponse response = new NotificationResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
//		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//}
