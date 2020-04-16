package com.synechron.insurancebazaar.exception;

import lombok.Data;

@Data
public class ObjectNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public ObjectNotFoundException() {
		super();
	}

	public ObjectNotFoundException(String message) {
		super();
		this.message = message;
	}

}
