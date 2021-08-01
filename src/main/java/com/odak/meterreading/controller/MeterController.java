package com.odak.meterreading.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.odak.meterreading.entity.MeterEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.service.MeterService;

@Controller
@RequestMapping("api/v1")
public class MeterController {

	private MeterService meterService;

	@Autowired
	public MeterController(MeterService meterService) {
		this.meterService = meterService;
	}

	@GetMapping("/meter-readings")
	public ResponseEntity<Page<MeterEntity>> getCatalogItems(@RequestParam HashMap<String, String> queryParams)
			throws BadRequestException {

		Page<MeterEntity> meterReadings = meterService.query(queryParams);

		return ResponseEntity.ok(meterReadings);
	}
}
