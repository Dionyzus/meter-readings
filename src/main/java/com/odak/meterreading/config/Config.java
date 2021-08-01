package com.odak.meterreading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.odak.meterreading.repository.MeterRepository;
import com.odak.meterreading.service.MeterService;

@Configuration
@ComponentScan("com.odak.meterreading.repository")
public class Config {

	@Bean
	public MeterService meterService(MeterRepository meterRepository) {
		return new MeterService(meterRepository);
	}
}
