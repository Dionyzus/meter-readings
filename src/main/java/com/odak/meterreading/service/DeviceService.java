package com.odak.meterreading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.odak.meterreading.entity.DeviceEntity;
import com.odak.meterreading.entity.MeterEntity;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.repository.device.DeviceRepository;

public class DeviceService {

	private DeviceRepository deviceRepository;

	@Autowired
	public DeviceService(DeviceRepository deviceRepository) {
		this.deviceRepository = deviceRepository;
	}

	public DeviceEntity getDeviceById(Long deviceId) {
		return deviceRepository.findById(deviceId).orElseThrow(
				() -> new ResourceNotFoundException("Device with provided id does not exist: " + deviceId));
	}

	public List<MeterEntity> getMeterEntityCollection(Long deviceId) {
		DeviceEntity device = getDeviceById(deviceId);

		return device.getMeterCollection();
	}

	public List<DeviceEntity> getDeviceCollection() {
		return deviceRepository.findAll();
	}
}
