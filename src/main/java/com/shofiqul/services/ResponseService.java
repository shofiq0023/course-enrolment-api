package com.shofiqul.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseService {
	private Map<String, Object> output = new HashMap<String, Object>();
	private final String DATA = "data";
	private final String MESSAGE = "message";
	private final String STATUS = "status";

	public ResponseEntity<Map<String, Object>> createResponse(Object data, HttpStatus status) {
		return new ResponseEntity<Map<String, Object>>(addData(data, "Success", status), status);
	}

	public ResponseEntity<Map<String, Object>> createResponse(String message, HttpStatus status) {
		return new ResponseEntity<Map<String, Object>>(addData(new ArrayList<>(), message, status), status);
	}
	
	public ResponseEntity<Map<String, Object>> createResponse(Object data, String message, HttpStatus status) {
		return new ResponseEntity<Map<String, Object>>(addData(data, message, status), status);
	}
	
	public ResponseEntity<Map<String, Object>> createDuplicateKeyResponse(DataIntegrityViolationException e) {
		ResponseEntity<Map<String, Object>> res = null;
		Throwable rootCause = e.getCause();

		if (rootCause instanceof ConstraintViolationException) {
			ConstraintViolationException ex = (ConstraintViolationException) rootCause;
			if (ex.getSQLState().equals("23505")) {
				// Getting the index of the error message where the Key and values are shown
				// e.g Key (email)=(value) in error message
				int trimmedMessage = ex.getErrorMessage().indexOf("(");
				String errorMsg = ex.getErrorMessage().substring(trimmedMessage, ex.getErrorMessage().length() - 2);
				
				res = new ResponseEntity<Map<String, Object>>(
						addData(new ArrayList<>(), errorMsg, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

				return res;
			}
		}

		return res;
	}

	private Map<String, Object> addData(Object data, String message, HttpStatus status) {
		this.output.put(DATA, data);
		this.output.put(MESSAGE, message);
		this.output.put(STATUS, status.value());

		return output;
	}
}
