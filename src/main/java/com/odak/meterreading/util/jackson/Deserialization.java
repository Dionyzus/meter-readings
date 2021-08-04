package com.odak.meterreading.util.jackson;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odak.meterreading.exception.DeserializationException;

/**
 * Deserialization utility class.
 * 
 * @author ivano
 *
 */
public class Deserialization {

	private static final String EXCEPTION_MESSAGE = "An exception ocurred.\nFailed to deserialize data.";

	private Deserialization() {
		throw new UnsupportedOperationException("Utils class instantiation not allowed.");
	}

	/**
	 * Generic deserialization method, requires resource with data and type to deserialize data into.
	 * @param <T> - generic parameter type.
	 * @param resource - {@link URL}.
	 * @param target - class type to deserialize data into.
	 * @return instance containing deserialized data.
	 */
	public static <T> T deserialize(URL resource, Class<T> target) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(resource,
					objectMapper.getTypeFactory().constructType(Class.forName(target.getName())));
		} catch (IOException | ClassNotFoundException e) {
			throw new DeserializationException(EXCEPTION_MESSAGE, e);
		}
	}

	/**
	 * Generic deserialization method, requires resource with data and type to deserialize
	 * data into.
	 * @param <T> - generic parameter type.
	 * @param resource - {@link URL}.
	 * @param target - class type to deserialize data into.
	 * @return list containing read data.
	 */
	public static <T> List<T> deserializeToList(URL resource, Class<T> target) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(resource,
					objectMapper.getTypeFactory().constructCollectionType(List.class, Class.forName(target.getName())));
		} catch (IOException | ClassNotFoundException e) {
			throw new DeserializationException(EXCEPTION_MESSAGE, e);
		}
	}
}
