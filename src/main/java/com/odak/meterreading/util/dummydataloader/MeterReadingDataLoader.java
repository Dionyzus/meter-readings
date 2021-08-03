package com.odak.meterreading.util.dummydataloader;

import java.net.URL;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.odak.meterreading.entity.MeterEntity;
import com.odak.meterreading.repository.MeterRepository;
import com.odak.meterreading.util.jackson.Deserialization;

@Component
public class MeterReadingDataLoader {

	private static final String DATA_SOURCE = "static/meter_readings.json";

	@Bean
	public CommandLineRunner loadCatalogItemData(MeterRepository meterRepository) {

		URL resource = Thread.currentThread().getContextClassLoader().getResource(DATA_SOURCE);

		final List<MeterEntity> meterReadings = Deserialization.deserialize(resource, MeterEntity.class);

		return (args) -> {
			//To initialize db with some data
			////meterRepository.saveAll(meterReadings);
		};
	}
}
