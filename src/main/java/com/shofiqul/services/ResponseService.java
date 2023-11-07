package com.shofiqul.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseService {
	private Map<String, Object> output = new HashMap<String, Object>();
	private final String DATA = "data";
	private final String MESSAGE = "message";
	private final String STATUS = "status";

	public ResponseEntity<Map<String, Object>> createSuccessResponse(Object data, HttpStatus status) {
		return new ResponseEntity<Map<String, Object>>(addData(data, "Success", status), status);
	}

	public ResponseEntity<Map<String, Object>> createSuccessResponse(Object data, String message, HttpStatus status) {
		return new ResponseEntity<Map<String, Object>>(addData(data, message, status), status);
	}

	public ResponseEntity<Map<String, Object>> createFailedResponse(Object data, HttpStatus status) {
		return new ResponseEntity<Map<String, Object>>(addData(data, "Failed", status), status);
	}

	public ResponseEntity<Map<String, Object>> createFailedResponse(Object data, String message, HttpStatus status) {
		return new ResponseEntity<Map<String, Object>>(addData(data, message, status), status);
	}

	private Map<String, Object> addData(Object data, String message, HttpStatus status) {
		this.output.put(DATA, data);
		this.output.put(MESSAGE, message);
		this.output.put(STATUS, status);

		return output;
	}
}
