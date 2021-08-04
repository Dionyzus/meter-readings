package com.odak.meterreading.exception;

/**
 * Base class for resource not found.
 *
 * @author ivano
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
