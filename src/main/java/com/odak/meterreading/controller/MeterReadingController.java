package com.odak.meterreading.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.odak.meterreading.entity.MeterReadingEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.service.MeterReadingService;

@Controller
@RequestMapping("api/v1")
public class MeterReadingController {

	private MeterReadingService meterReadingService;

	@Autowired
	public MeterReadingController(MeterReadingService meterReadingService) {
		this.meterReadingService = meterReadingService;
	}

	@GetMapping("/catalog-items")
	public ResponseEntity<Page<MeterReadingEntity>> getCatalogItems(@RequestParam HashMap<String, String> queryParams)
			throws BadRequestException {

		Page<MeterReadingEntity> meterReadings = meterReadingService.query(queryParams);

		return ResponseEntity.ok(meterReadings);
	}
}
