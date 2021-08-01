package com.odak.meterreading.exception;

public class DeserializationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DeserializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
