package com.odak.meterreading.util.dummydataloader;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.odak.meterreading.entity.Address;
import com.odak.meterreading.entity.ClientEntity;
import com.odak.meterreading.entity.DeviceEntity;
import com.odak.meterreading.entity.MeterReadingEntity;
import com.odak.meterreading.repository.client.ClientRepository;
import com.odak.meterreading.repository.device.DeviceRepository;
import com.odak.meterreading.repository.meter.MeterReadingRepository;

/**
 * Class to load initial data, now using flyway, but keeping it just in case.
 *
 * @author ivano
 *
 */
@Component
public class InitialDataLoader {

	/**
	 * Loads initial data.
	 *
	 * @param deviceRepository       - {@link DeviceRepository}.
	 * @param clientRepository       - {@link ClientRepository}.
	 * @param meterReadingRepository - {@link MeterReadingRepository}.
	 * @return {@link CommandLineRunner}.
	 */
	@Bean
	public CommandLineRunner loadDeviceData(DeviceRepository deviceRepository, ClientRepository clientRepository,
			MeterReadingRepository meterReadingRepository) {

		return (args) -> {
//			DeviceEntity deviceOne = new DeviceEntity();
//			DeviceEntity deviceTwo = new DeviceEntity();
//
//			deviceRepository.saveAll(List.of(deviceOne, deviceTwo));
//
//			ClientEntity clientOne = new ClientEntity();
//			clientOne.setFullName("Client one");
//			clientOne.setDeviceId(deviceOne.getId());
//			clientOne.setAddress(new Address("Street", "City", "10000"));
//
//			clientRepository.save(clientOne);
//
//			MeterReadingEntity meterReadingOne = new MeterReadingEntity();
//			meterReadingOne.setDevice(deviceOne);
//			meterReadingOne.setReadingTime(LocalDate.of(2021, 5, 1));
//			meterReadingOne.setReadingValue(Double.valueOf(100));
//
//			MeterReadingEntity meterReadingTwo = new MeterReadingEntity();
//			meterReadingTwo.setDevice(deviceOne);
//			meterReadingTwo.setReadingTime(LocalDate.of(2021, 6, 1));
//			meterReadingTwo.setReadingValue(Double.valueOf(75));
//
//			MeterReadingEntity meterReadingThree = new MeterReadingEntity();
//			meterReadingThree.setDevice(deviceOne);
//			meterReadingThree.setReadingTime(LocalDate.of(2021, 7, 1));
//			meterReadingThree.setReadingValue(Double.valueOf(165));
//
//			meterReadingRepository.saveAll(List.of(meterReadingOne, meterReadingTwo, meterReadingThree));
		};
	}
}
