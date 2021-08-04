package com.odak.meterreading.exception;

/**
 * Base class for JSON deserialization exceptions.
 *
 * @author ivano
 *
 */
public class DeserializationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DeserializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
