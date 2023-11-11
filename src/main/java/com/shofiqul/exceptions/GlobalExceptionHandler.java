package com.shofiqul.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shofiqul.services.ResponseService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	private final ResponseService resService;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeneralException() {
		return resService.createResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
