package com.odak.meterreading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.odak.meterreading.entity.DeviceEntity;
import com.odak.meterreading.entity.MeterReadingEntity;
import com.odak.meterreading.service.DeviceService;

/**
 * Device controller working with {@link DeviceEntity}. Contains end-points
 * available to work with device data.
 *
 * @author ivano
 *
 */
@RestController
@RequestMapping("api/v1")
public class DeviceController {

	private DeviceService deviceService;

	@Autowired
	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	/**
	 * Gets {@link DeviceEntity} collection.
	 * 
	 * @return {@link ResponseEntity} - JSON response containing list of device
	 *         entries.
	 */
	@RequestMapping(value = "/devices", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<List<DeviceEntity>> getDevices() {
		return ResponseEntity.ok(deviceService.getDeviceCollection());
	}

	/**
	 * Gets {@link MeterReadingEntity} collection.
	 *
	 * @param deviceId - device id of a requested entry.
	 * @return {@link ResponseEntity} - JSON response containing list of meter
	 *         readings for requested device entry if any matches provided id,
	 *         throws exception otherwise.
	 */
	@RequestMapping(value = "/devices/{id}/meter-readings", method = RequestMethod.GET, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<List<MeterReadingEntity>> getDeviceMeterReadings(@PathVariable(value = "id") Long deviceId) {

		return ResponseEntity.ok(deviceService.getMeterReadingCollection(deviceId));
	}
}
