package com.odak.meterreading.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.odak.meterreading.entity.MeterEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.repository.MeterRepository;

public class MeterService {

	private static final Integer DEFAULT_RECORDS_LIMIT = 5;
	private static final Integer DEFAULT_PAGE_OFFSET = 0;

	private static final String EXCEPTION_MESSAGE = "Resource with given id not found: ";

	private MeterRepository meterRepository;

	@Autowired
	public MeterService(MeterRepository meterRepository) {
		this.meterRepository = meterRepository;
	}

	public MeterEntity create(MeterEntity meterReadingEntity) {

		Optional<MeterEntity> meterEntity = meterRepository.findById(meterReadingEntity.getId());

		if (meterEntity.isPresent()) {
			throw new BadRequestException("Record with given id already exists: " + meterReadingEntity.getId());
		}

		return meterRepository.save(meterReadingEntity);
	}

	public Page<MeterEntity> query(HashMap<String, String> queryParams) {

		if (queryParams == null || queryParams.isEmpty()) {
			PageRequest pageable = PageRequest.of(DEFAULT_PAGE_OFFSET, DEFAULT_RECORDS_LIMIT);
			return meterRepository.findAll(pageable);
		}

		String searchType = queryParams.get("type") != null ? queryParams.get("type") : "";
		String searchValue = queryParams.get("value") != null ? queryParams.get("value") : "";

		Integer limit = queryParams.get("limit") != null ? Integer.valueOf(queryParams.get("limit"))
				: DEFAULT_RECORDS_LIMIT;
		Integer offset = queryParams.get("offset") != null ? Integer.valueOf(queryParams.get("offset"))
				: DEFAULT_PAGE_OFFSET;

		String sortField = queryParams.get("sortBy") != null ? queryParams.get("sortBy") : "";
		String sortDirection = queryParams.get("sortDir") != null ? queryParams.get("sortDir") : "";

		if (sortField != "") {
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.toString()) ? Sort.by(sortField).descending()
					: Sort.by(sortField).ascending();

			PageRequest pageable = PageRequest.of(offset, limit, sort);
			return meterRepository.findAll(pageable);
		}

		PageRequest pageable = PageRequest.of(offset, limit);

		return meterRepository.findAll(pageable);

	}

	public MeterEntity getMeterReadingById(Long meterReadingId) {
		return meterRepository.findById(meterReadingId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + meterReadingId));
	}

	public MeterEntity update(Long meterEntityId, MeterEntity meterEntityDetails) {
		MeterEntity meterEntity = meterRepository.findById(meterEntityId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + meterEntityId));

		return updateEntity(meterEntity, meterEntityDetails);
	}

	private MeterEntity updateEntity(MeterEntity meterEntity, MeterEntity meterEntityDetails) {
		meterEntity.setReadingValue(meterEntityDetails.getReadingValue());
		meterEntity.setReadingTime(meterEntityDetails.getReadingTime());

		return meterRepository.save(meterEntity);
	}

	public void delete(Long meterEntityId) {
		MeterEntity meterEntity = meterRepository.findById(meterEntityId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + meterEntityId));

		meterRepository.delete(meterEntity);
	}
}
