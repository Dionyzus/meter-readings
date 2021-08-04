package com.odak.meterreading.helper.query.operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.repository.meter.MeterReadingRepository;
import com.odak.meterreading.repository.meter.QueryResult;

/**
 * Interface defining abstraction to perform query operation.
 *
 * @author ivano
 *
 */
public interface MeterReadingOperation {
	/**
	 * Operation performing meter reading collection query according to provided
	 * criteria.
	 * 
	 * @param meterReadingRepository - {@link MeterReadingRepository} instance.
	 * @param pageable               - {@link Pageable} instance.
	 * @param criteria               - String value representing query criteria.
	 * @return {@link Page} instance containing query result.
	 * @throws BadRequestException - exception if operation fails, eg. bad query
	 *                             parameters.
	 */
	Page<QueryResult> view(MeterReadingRepository meterReadingRepository, Pageable pageable, String criteria)
			throws BadRequestException;
}
