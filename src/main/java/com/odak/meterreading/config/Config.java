package com.odak.meterreading.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.odak.meterreading.repository.client.ClientRepository;
import com.odak.meterreading.repository.device.DeviceRepository;
import com.odak.meterreading.repository.meter.MeterReadingRepository;
import com.odak.meterreading.service.ClientService;
import com.odak.meterreading.service.DeviceService;
import com.odak.meterreading.service.MeterReadingService;

/**
 * Configuration class where necessary beans are added.
 *
 * @author ivano
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.odak.meterreading.repository")
public class Config {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public MeterReadingService meterReadingService(MeterReadingRepository meterReadingRepository, DeviceService deviceService) {
		return new MeterReadingService(meterReadingRepository, deviceService);
	}

	@Bean
	public ClientService clientService(ClientRepository clientRepository, DeviceService deviceService) {
		return new ClientService(clientRepository, deviceService);
	}

	@Bean
	public DeviceService deviceService(DeviceRepository deviceRepository) {
		return new DeviceService(deviceRepository);
	}
}
