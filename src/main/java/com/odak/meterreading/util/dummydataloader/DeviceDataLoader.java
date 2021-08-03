package com.odak.meterreading.util.dummydataloader;

import java.net.URL;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.odak.meterreading.entity.DeviceEntity;
import com.odak.meterreading.repository.device.DeviceRepository;
import com.odak.meterreading.util.jackson.Deserialization;

@Component
public class DeviceDataLoader {

	private static final String DATA_SOURCE = "static/devices.json";

	@Bean
	public CommandLineRunner loadDeviceData(DeviceRepository deviceRepository) {

		URL resource = Thread.currentThread().getContextClassLoader().getResource(DATA_SOURCE);

		List<DeviceEntity> deviceCollection = Deserialization.deserializeToList(resource, DeviceEntity.class);

		return (args) -> {
			//To initialize db with some data
			//deviceRepository.saveAll(deviceCollection);
		};
	}
}
