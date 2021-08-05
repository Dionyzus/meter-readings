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
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.service.DeviceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

	@Operation(summary = "Get all devices")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "device collection", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DeviceEntity.class))) }), })
	/**
	 * Gets {@link DeviceEntity} collection.
	 * 
	 * @return {@link ResponseEntity} - JSON response containing list of device
	 *         entries.
	 */
	@RequestMapping(value = "/devices", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<List<DeviceEntity>> getDevices() {
		return ResponseEntity.ok(deviceService.findAll());
	}

	@Operation(summary = "Get device by device id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "device entry", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DeviceEntity.class)) }),
			@ApiResponse(responseCode = "400", description = "device id is required", content = @Content),
			@ApiResponse(responseCode = "404", description = "device with provided id not found", content = @Content) })
	/**
	 * Gets device by id.
	 * 
	 * @param deviceId - device id to query collection with.
	 * @return {@link DeviceEntity} matching provided id, throws exception
	 *         otherwise.
	 * @throws ResourceNotFoundException - exception if device with provided id does
	 *                                   not exist.
	 */
	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<DeviceEntity> getDeviceById(@PathVariable(value = "id") Long deviceId)
			throws ResourceNotFoundException {

		DeviceEntity deviceEntity = deviceService.getDeviceById(deviceId);

		return ResponseEntity.ok(deviceEntity);
	}

	@Operation(summary = "Get meter readings associated with device")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "meter reading collection for device", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MeterReadingEntity.class))) }),
			@ApiResponse(responseCode = "400", description = "device id is required", content = @Content),
			@ApiResponse(responseCode = "404", description = "device with provided id not found", content = @Content) })
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
