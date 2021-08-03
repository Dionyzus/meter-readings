package com.odak.meterreading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.odak.meterreading.entity.DeviceEntity;
import com.odak.meterreading.entity.MeterEntity;
import com.odak.meterreading.service.DeviceService;

@RestController
@RequestMapping("api/v1")
public class DeviceController {

	private DeviceService deviceService;

	@Autowired
	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	@RequestMapping(value = "/devices", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<List<DeviceEntity>> getDevices() {
		return ResponseEntity.ok(deviceService.getDeviceCollection());
	}

	@RequestMapping(value = "/devices/{id}/meter-readings", method = RequestMethod.GET, produces = {
			"application/json" })
	public ResponseEntity<List<MeterEntity>> getDeviceMeterReadings(@PathVariable(value = "id") Long deviceId) {

		return ResponseEntity.ok(deviceService.getMeterEntityCollection(deviceId));
	}
}
