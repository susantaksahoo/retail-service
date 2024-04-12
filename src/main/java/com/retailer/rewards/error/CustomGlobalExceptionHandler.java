package com.retailer.rewards.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@RestControllerAdvice
	public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

		@ExceptionHandler(Exception.class)
		public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest request) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
					request.getDescription(false));

			return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		@ExceptionHandler(CustomerNotFoundException.class)
		public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(
				CustomerNotFoundException customerNotFoundException, WebRequest request) {
			ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
					customerNotFoundException.getMessage(), request.getDescription(false));

			return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
		}
	}
}
