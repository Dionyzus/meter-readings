package com.odak.meterreading.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;

import com.odak.meterreading.entity.MeterEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.helper.query.builder.QueryBuilderImpl;
import com.odak.meterreading.helper.query.builder.QueryBuilderImpl.QueryConfiguration;
import com.odak.meterreading.helper.query.operation.MeterReadingOperation;
import com.odak.meterreading.helper.query.operation.MeterReadingOperationFactory;
import com.odak.meterreading.repository.MeterRepository;
import com.odak.meterreading.repository.QueryResult;
import com.odak.meterreading.util.string.StringUtil;

public class MeterService {

	private static final Integer DEFAULT_RECORDS_LIMIT = 5;
	private static final Integer DEFAULT_PAGE_OFFSET = 0;

	private static final String EXAMPLE_USAGE = "Usage: eq. type=aggr&value=2021";
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

	public QueryConfiguration buildQuery(HashMap<String, String> queryParams) {
		return QueryBuilderImpl.QueryConfiguration.of(queryParams.get("type"), queryParams.get("value"),
				StringUtil.tryParseInteger(queryParams.get("limit"), DEFAULT_RECORDS_LIMIT),
				StringUtil.tryParseInteger(queryParams.get("offset"), DEFAULT_PAGE_OFFSET), queryParams.get("sortBy"),
				queryParams.get("sortDir"));
	}

	public Page<QueryResult> query(QueryConfiguration query) {

		if (StringUtil.isNullOrEmpty(query.value)) {
			throw new BadRequestException("Query type exists, but no value provided. " + EXAMPLE_USAGE);
		}

		MeterReadingOperation targetOperation = MeterReadingOperationFactory.getOperation(query.type)
				.orElseThrow(() -> new BadRequestException("Invalid query type provided: " + query.type));

		PageRequest pageable = PageRequest.of(query.offset, query.limit);

		return targetOperation.view(meterRepository, pageable, query.value);
	}

	public Page<MeterEntity> findAll(QueryConfiguration query) {

		PageRequest pageable = PageRequest.of(query.offset, query.limit);

		if (!StringUtil.isNullOrEmpty(query.sortBy)) {

			Sort sort;
			if (!StringUtil.isNullOrEmpty(query.sortDirection)) {
				sort = query.sortDirection.equalsIgnoreCase(Sort.Direction.DESC.toString())
						? Sort.by(query.sortBy).descending()
						: Sort.by(query.sortBy).ascending();
			} else {
				sort = Sort.by(query.sortBy).ascending();
			}
			try {
				return meterRepository.findAll(pageable.withSort(sort));
			} catch (PropertyReferenceException e) {
				throw new BadRequestException("Wrong sort field. Usage: eq. sortBy=readingValue");
			}
		}

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
