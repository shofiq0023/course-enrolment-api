package com.shofiqul.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class ResponseService {
	private Map<String, Object> output = new HashMap<String, Object>();
	private final String DATA = "data";
	private final String MESSAGE = "message";
	private final String STATUS = "status";
	
	
	public ResponseEntity<Map<String, Object>> createSuccessResponse(Object data, HttpStatus status) {
		addData(data, "Success", status);
		return new ResponseEntity<Map<String,Object>>(output, status);
	}
	
	public ResponseEntity<Map<String, Object>> createSuccessResponse(Object data, String message, HttpStatus status) {
		addData(data, message, status);
		return new ResponseEntity<Map<String,Object>>(output, status);
	}
	
	private Map<String, Object> addData(Object data, String message, HttpStatus status) {
		this.output.put(DATA, data);
		this.output.put(MESSAGE, "Success");
		this.output.put(STATUS, status);
		
		return output;
	}
}
