package com.example.handson.exception;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(CustomerNotFoundException.class)
	public void handleNotFound(CustomerNotFoundException ex) {
		logger.error("Resource not found");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(InvalidCustomerRequestException.class)
	public void handleBadRequest(InvalidCustomerRequestException ex) {
		logger.error("Invalid Fund Request");
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	public void handleGeneralError(Exception ex) {
		logger.error("An error occurred procesing request", ex);
	}
}
