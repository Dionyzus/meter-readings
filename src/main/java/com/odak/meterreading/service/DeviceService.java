package com.odak.meterreading.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import com.odak.meterreading.controller.DeviceController;
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

	/**
	 * Gets device entity collection as list.
	 * 
	 * @return list containing {@link DeviceEntity} entries.
	 */
	public List<DeviceEntity> findAll() {
		return deviceRepository.findAll();
	}

	/**
	 * Creates links for device entity.
	 * 
	 * @param devices - {@link DeviceEntity} collection
	 * @return {@link CollectionModel} containing device entities with links
	 *         associated with them.
	 */
	public CollectionModel<DeviceEntity> createLinksForDevices(List<DeviceEntity> devices) {
		for (final DeviceEntity device : devices) {
			Long deviceId = device.getId();
			Link selfLink = linkTo(DeviceController.class).slash(deviceId).withSelfRel();
			device.add(selfLink);
			if (device.getMeterReadingCollection().size() > 0) {
				final Link meterReadingsLink = linkTo(methodOn(DeviceController.class).getDeviceMeterReadings(deviceId))
						.withRel("allMeterReadings");
				device.add(meterReadingsLink);
			}
		}

		Link link = linkTo(DeviceController.class).withSelfRel();
		CollectionModel<DeviceEntity> result = CollectionModel.of(devices, link);
		return result;
	}
}
