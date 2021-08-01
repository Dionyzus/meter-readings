package com.odak.meterreading.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.odak.meterreading.entity.MeterEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.service.MeterService;

@RestController
@RequestMapping("api/v1")
public class MeterController {

	private MeterService meterService;

	@Autowired
	public MeterController(MeterService meterService) {
		this.meterService = meterService;
	}

	@RequestMapping(value = "/meter-readings", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = "application/json")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<MeterEntity> addMeterReading(@Validated @RequestBody MeterEntity meterEntityDetails) {

		MeterEntity meterEntity = meterService.create(meterEntityDetails);

		return ResponseEntity.status(HttpStatus.CREATED).body(meterEntity);
	}

	@RequestMapping(value = "/meter-readings", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Page<MeterEntity>> getMeterReadings(@RequestParam HashMap<String, String> queryParams)
			throws BadRequestException {

		Page<MeterEntity> meterReadings = meterService.query(queryParams);

		return ResponseEntity.ok(meterReadings);
	}

	@RequestMapping(value = "/meter-readings/{id}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<MeterEntity> getMeterReadingById(@PathVariable(value = "id") String meterReadingId)
			throws ResourceNotFoundException {

		MeterEntity meterEntity = meterService.getMeterReadingById(meterReadingId);

		return ResponseEntity.ok(meterEntity);
	}

	@RequestMapping(value = "/meter-readings/{id}", method = RequestMethod.PUT, produces = {
			"application/json" }, consumes = "application/json")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<MeterEntity> updateMeterReading(@PathVariable(value = "id") String meterEntityId,
			@RequestBody MeterEntity meterEntityDetails) throws ResourceNotFoundException {

		MeterEntity meterEntity = meterService.update(meterEntityId, meterEntityDetails);

		return ResponseEntity.ok(meterEntity);
	}

	@RequestMapping(value = "/meter-readings/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<MeterEntity> deleteMeterReading(@PathVariable(value = "id") String meterEntityId)
			throws ResourceNotFoundException {

		meterService.delete(meterEntityId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
