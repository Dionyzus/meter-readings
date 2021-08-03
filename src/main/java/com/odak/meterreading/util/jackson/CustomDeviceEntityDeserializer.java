package com.odak.meterreading.util.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.odak.meterreading.entity.DeviceEntity;

public class CustomDeviceEntityDeserializer extends JsonDeserializer<DeviceEntity> {
	@Override
	public DeviceEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		if (jsonParser == null)
			return null;

		DeviceEntity device = new DeviceEntity();
		device.setId(Long.valueOf(jsonParser.getText()));
		return device;
	}
}
