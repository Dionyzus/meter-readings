package com.odak.meterreading.util.jackson;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odak.meterreading.exception.DeserializationException;

public class Deserialization {

	private static final String EXCEPTION_MESSAGE = "An exception ocurred.\nFailed to deserialize data.";

	private Deserialization() {
		throw new UnsupportedOperationException("Utils class instantiation not allowed.");
	}

	public static <T> List<T> deserialize(URL resource, Class<T> target) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(resource,
					objectMapper.getTypeFactory().constructCollectionType(List.class, Class.forName(target.getName())));
		} catch (IOException | ClassNotFoundException e) {
			throw new DeserializationException(EXCEPTION_MESSAGE, e);
		}
	}
}
