package com.shofiqul.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shofiqul.services.ResponseService;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	private final ResponseService resService;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeneralException() {
		return resService.createResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(TokenMissingException.class)
	public ResponseEntity<?> handleTokenMissingException(TokenMissingException e) {
		return resService.createResponse(e.getMessage(), HttpStatus.FORBIDDEN);
	}

}
