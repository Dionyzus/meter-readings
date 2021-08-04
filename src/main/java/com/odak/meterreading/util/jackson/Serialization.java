package com.odak.meterreading.util.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odak.meterreading.exception.DeserializationException;

/**
 * Serialization utility class.
 * 
 * @author ivano
 *
 */
public class Serialization {

	private static final String EXCEPTION_MESSAGE = "An exception ocurred.\nFailed to serialize data.";

	private Serialization() {
		throw new UnsupportedOperationException("Utils class instantiation not allowed.");
	}

	/**
	 * Serializes provided object into json node.
	 * 
	 * @param source - {@link Object} source containing data for serialization.
	 * @return {@link JsonNode} instance.
	 */
	public static JsonNode serialize(Object source) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.valueToTree(source);
		} catch (IllegalArgumentException e) {
			throw new DeserializationException(EXCEPTION_MESSAGE, e);
		}
	}
}
