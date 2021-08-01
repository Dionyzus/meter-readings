package com.odak.meterreading.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.odak.meterreading.entity.MeterEntity;
import com.odak.meterreading.repository.MeterRepository;

public class MeterService {
	
	private static final Integer DEFAULT_RECORDS_LIMIT = 5;
	private static final Integer DEFAULT_PAGE_OFFSET = 0;
	
	private MeterRepository meterReadingRepository;
	
	@Autowired
	public MeterService(MeterRepository meterReadingRepository) {
		this.meterReadingRepository = meterReadingRepository;
	}

	public MeterEntity create(MeterEntity meterReadingEntity) {
		return meterReadingRepository.save(meterReadingEntity);
	}

	public Page<MeterEntity> query(HashMap<String, String> queryParams) {
		
		PageRequest pageable = PageRequest.of(DEFAULT_PAGE_OFFSET, DEFAULT_RECORDS_LIMIT);
		return meterReadingRepository.findAll(pageable);
	}

}
