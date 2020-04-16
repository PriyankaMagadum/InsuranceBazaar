package com.synechron.insurancebazaar.config;

import lombok.Data;

@Data
public class Response {

	private String message;

	private Object data;

	public Response() {
		super();
	}

	public Response(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}

}
