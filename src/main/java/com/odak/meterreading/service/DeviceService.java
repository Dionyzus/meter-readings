package com.odak.meterreading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.odak.meterreading.entity.DeviceEntity;
import com.odak.meterreading.entity.MeterReadingEntity;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.repository.device.DeviceRepository;

/**
 * Service handling {@link DeviceEntity} REST API calls.
 * 
 * @author ivano
 *
 */
public class DeviceService {

	private DeviceRepository deviceRepository;

	@Autowired
	public DeviceService(DeviceRepository deviceRepository) {
		this.deviceRepository = deviceRepository;
	}

	/**
	 * Gets device from collection.
	 * 
	 * @param deviceId - device id to query collection with.
	 * @return {@link DeviceEntity} instance if any matches provided id, throws
	 *         {@link ResourceNotFoundException} otherwise.
	 */
	public DeviceEntity getDeviceById(Long deviceId) {
		return deviceRepository.findById(deviceId).orElseThrow(
				() -> new ResourceNotFoundException("Device with provided id does not exist: " + deviceId));
	}

	/**
	 * Gets meter readings for device.
	 * 
	 * @param deviceId - device id to query collection with.
	 * @return {@link MeterReadingEntity} collection for device if any matches
	 *         provided id.
	 */
	public List<MeterReadingEntity> getMeterReadingCollection(Long deviceId) {
		DeviceEntity device = getDeviceById(deviceId);

		return device.getMeterReadingCollection();
	}

	public List<DeviceEntity> findAll() {
		return deviceRepository.findAll();
	}
}
