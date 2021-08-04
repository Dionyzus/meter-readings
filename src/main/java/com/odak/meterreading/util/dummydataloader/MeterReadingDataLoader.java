package com.odak.meterreading.util.dummydataloader;

import java.net.URL;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.odak.meterreading.entity.MeterReadingEntity;
import com.odak.meterreading.repository.meter.MeterReadingRepository;
import com.odak.meterreading.util.jackson.Deserialization;

@Component
public class MeterReadingDataLoader {

	private static final String DATA_SOURCE = "static/meter_readings.json";

	/**
	 * Loads initial meter readings for device.
	 *
	 * @param meterReadingRepository - repository handling data.
	 * @return {@link CommandLineRunner}.
	 */
	@Bean
	public CommandLineRunner loadMeterReadingData(MeterReadingRepository meterReadingRepository) {

		URL resource = Thread.currentThread().getContextClassLoader().getResource(DATA_SOURCE);

		List<MeterReadingEntity> meterEntityCollection = Deserialization.deserializeToList(resource,
				MeterReadingEntity.class);

		return (args) -> {
			// To initialize db with some data
//			meterReadingRepository.saveAll(meterEntityCollection);
		};
	}
}
