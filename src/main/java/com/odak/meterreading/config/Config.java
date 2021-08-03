package com.odak.meterreading.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.odak.meterreading.repository.client.ClientRepository;
import com.odak.meterreading.repository.device.DeviceRepository;
import com.odak.meterreading.repository.meter.MeterRepository;
import com.odak.meterreading.service.ClientService;
import com.odak.meterreading.service.DeviceService;
import com.odak.meterreading.service.MeterService;

@Configuration
@EnableJpaRepositories(basePackages = "com.odak.meterreading.repository")
public class Config {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public MeterService meterService(MeterRepository meterRepository) {
		return new MeterService(meterRepository);
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
