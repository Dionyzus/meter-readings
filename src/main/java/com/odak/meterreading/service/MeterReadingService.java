package com.odak.meterreading.service;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;

import com.odak.meterreading.entity.DeviceEntity;
import com.odak.meterreading.entity.MeterReadingEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.helper.query.builder.QueryBuilderImpl;
import com.odak.meterreading.helper.query.builder.QueryBuilderImpl.QueryConfiguration;
import com.odak.meterreading.helper.query.operation.MeterReadingOperation;
import com.odak.meterreading.helper.query.operation.MeterReadingOperationFactory;
import com.odak.meterreading.model.MeterReadingDto;
import com.odak.meterreading.repository.meter.MeterReadingRepository;
import com.odak.meterreading.repository.meter.QueryResult;
import com.odak.meterreading.util.string.StringUtil;

/**
 * Service handling {@link MeterReadingEntity} REST API calls.
 * 
 * @author ivano
 *
 */
public class MeterReadingService {

	private static final Integer DEFAULT_RECORDS_LIMIT = 5;
	private static final Integer DEFAULT_PAGE_OFFSET = 0;

	private static final String EXAMPLE_USAGE = "Usage: eq. type=aggr&value=2021";
	private static final String EXCEPTION_MESSAGE = "Resource with given id not found: ";

	private MeterReadingRepository meterReadingRepository;
	private DeviceService deviceService;

	@Autowired
	public MeterReadingService(MeterReadingRepository meterReadingRepository, DeviceService deviceService) {
		this.meterReadingRepository = meterReadingRepository;
		this.deviceService = deviceService;
	}

	/**
	 * Adds new reading to device.
	 *
	 * @param meterReadingDto - {@link MeterReadingDto} containing data for new collection entry.
	 * @return {@link MeterReadingEntity} if operation was successful.
	 */
	public MeterReadingEntity create(MeterReadingDto meterReadingDto) {

		MeterReadingEntity meterReadingEntity = convertToEntity(meterReadingDto);

		validateEntry(meterReadingEntity);

		return meterReadingRepository.save(meterReadingEntity);
	}

	/**
	 * Converts from data transfer object to {@link MeterReadingEntity} instance.
	 * 
	 * @param meterReadingDto - {@link MeterReadingDto} instance.
	 * @return {@link MeterReadingEntity} instance if operation was successful,
	 *         throws exception otherwise.
	 */
	public MeterReadingEntity convertToEntity(MeterReadingDto meterReadingDto) {
		ModelMapper modelMapper = new ModelMapper();

		MeterReadingEntity meterReadingEntity = modelMapper.map(meterReadingDto, MeterReadingEntity.class);

		if (meterReadingDto.getDeviceId() != null) {
			DeviceEntity device = deviceService.getDeviceById(meterReadingDto.getDeviceId());
			meterReadingEntity.setDevice(device);
		}

		try {
			meterReadingEntity.setReadingTime(meterReadingDto.convertToLocalDate());
		} catch (DateTimeParseException exception) {
			throw new BadRequestException(
					"Provided data is not valid, missing year or month. Usage: eg. year: 2021, month: 07, reading_value: 50.5");
		}

		return meterReadingEntity;
	}

	/**
	 * Builds query from provided query parameters.
	 *
	 * @param queryParams - map containing key value pairs of provided query
	 *                    parameters.
	 * @return {@link QueryConfiguration} instance.
	 */
	public QueryConfiguration buildQuery(HashMap<String, String> queryParams) {
		return QueryBuilderImpl.QueryConfiguration.of(queryParams.get("type"), queryParams.get("value"),
				StringUtil.tryParseInteger(queryParams.get("limit"), DEFAULT_RECORDS_LIMIT),
				StringUtil.tryParseInteger(queryParams.get("offset"), DEFAULT_PAGE_OFFSET), queryParams.get("sortBy"),
				queryParams.get("sortDir"));
	}

	/**
	 * Queries {@link MeterReadingEntity} collection according to query
	 * configuration instance.
	 * 
	 * @param query - {@link QueryConfiguration} instance containing query
	 *              information.
	 * @return {@link Page} containing query result data.
	 */
	public Page<QueryResult> query(QueryConfiguration query) {

		if (StringUtil.isNullOrEmpty(query.value)) {
			throw new BadRequestException("Query type exists, but no value provided. " + EXAMPLE_USAGE);
		}

		MeterReadingOperation targetOperation = MeterReadingOperationFactory.getOperation(query.type)
				.orElseThrow(() -> new BadRequestException("Invalid query type provided: " + query.type));

		PageRequest pageable = PageRequest.of(query.offset, query.limit);

		return targetOperation.view(meterReadingRepository, pageable, query.value);
	}

	/**
	 * Queries {@link MeterReadingEntity} collection according to query
	 * configuration instance.
	 * 
	 * @param query - {@link QueryConfiguration} instance containing query
	 *              information.
	 * @return {@link Page} containing fetched {@link MeterReadingEntity} collection
	 *         data.
	 */
	public Page<MeterReadingEntity> findAll(QueryConfiguration query) {

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
				return meterReadingRepository.findAll(pageable.withSort(sort));
			} catch (PropertyReferenceException e) {
				throw new BadRequestException("Wrong sort field. Usage: eq. sortBy=readingValue");
			}
		}

		return meterReadingRepository.findAll(pageable);
	}

	/**
	 * Gets meter reading entry.
	 * 
	 * @param meterReadingId - meter reading id value to query collection with.
	 * @return {@link MeterReadingEntity} instance if operation was successful,
	 *         throws {@link ResourceNotFoundException} exception otherwise.
	 */
	public MeterReadingEntity getMeterReadingById(Long meterReadingId) {
		return meterReadingRepository.findById(meterReadingId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + meterReadingId));
	}

	/**
	 * Updates meter reading collection entry.
	 * 
	 * @param meterEntityId       - meter reading id value of entry to be updated.
	 * @param meterReadingDetails - {@link MeterReadingDto} updated entry details.
	 * @return {@link MeterReadingEntity} instance if operation was successful,
	 *         throws {@link ResourceNotFoundException} exception otherwise.
	 */
	public MeterReadingEntity update(Long meterEntityId, MeterReadingDto meterReadingDetails) {
		MeterReadingEntity meterReadingEntity = meterReadingRepository.findById(meterEntityId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + meterEntityId));

		MeterReadingEntity updatedMeterReading = convertToEntity(meterReadingDetails);

		return updateEntity(meterReadingEntity, updatedMeterReading);
	}

	/**
	 * Updates collection entry.
	 * 
	 * @param meterReadingEntity - {@link MeterReadingEntity} existing instance to
	 *                           be updated.
	 * @param meterEntityDetails - {@link MeterReadingEntity} containing updated
	 *                           values.
	 * @return {@link MeterReadingEntity} instance if operation was successful,
	 *         throws {@link BadRequestException} otherwise.
	 */
	private MeterReadingEntity updateEntity(MeterReadingEntity meterReadingEntity,
			MeterReadingEntity meterEntityDetails) {
		meterReadingEntity.setReadingValue(meterEntityDetails.getReadingValue());
		meterReadingEntity.setReadingTime(meterEntityDetails.getReadingTime());

		validateEntry(meterReadingEntity);

		return meterReadingRepository.save(meterReadingEntity);
	}

	/**
	 * Deletes entry from meter reading collection, throws exception if provided id
	 * value is invalid.
	 * 
	 * @param meterEntityId - meter reading id value of entry to be deleted
	 */
	public void delete(Long meterEntityId) {
		MeterReadingEntity meterReadingEntity = meterReadingRepository.findById(meterEntityId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + meterEntityId));

		meterReadingRepository.delete(meterReadingEntity);
	}

	/**
	 * Checks if reading value for provided month already exists.
	 * 
	 * @param meterReadingEntity
	 */
	private void validateEntry(MeterReadingEntity meterReadingEntity) {
		Optional<MeterReadingEntity> existingEntity = meterReadingRepository
				.findByReadingTime(meterReadingEntity.getReadingTime());

		if (existingEntity.isPresent()) {
			// This could be 409 conflict as well
			throw new BadRequestException(
					"Reading for the date already exists: " + meterReadingEntity.getReadingTime());
		}
	}
}
