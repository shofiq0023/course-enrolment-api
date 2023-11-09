package com.shofiqul.exceptions;

public class TokenMissingException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TokenMissingException() {}
	
	public TokenMissingException(String message) {
		super(message);
	}
}
