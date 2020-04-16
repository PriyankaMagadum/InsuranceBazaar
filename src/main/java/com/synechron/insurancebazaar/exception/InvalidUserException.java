package com.synechron.insurancebazaar.exception;

import lombok.Data;

@Data
public class InvalidUserException extends Exception{

	private static final long serialVersionUID = 1L;
	private String message;

	public InvalidUserException() {
		super();
	}

	public InvalidUserException(String message) {
		super();
		this.message = message;
	}

	
}
