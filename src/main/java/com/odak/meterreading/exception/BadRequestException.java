package com.odak.meterreading.exception;

/**
 * Base class for bad request exception.
 *
 * @author ivano
 *
 */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message);
	}
	
	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
