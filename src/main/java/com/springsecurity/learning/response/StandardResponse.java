package com.springsecurity.learning.response;

import lombok.Data;

@Data
public class StandardResponse {
	
	private boolean success;
	private String message;
	private Object data;
	
	public StandardResponse(boolean success, Object data) {
		super();
		this.success = success;
		this.data = data;
	}

	public StandardResponse(boolean success, String message, Object data) {
		this(success, data);
		this.message = message;
	}
}
