package com.odak.meterreading.helper.query.operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.repository.meter.MeterReadingRepository;
import com.odak.meterreading.repository.meter.QueryResult;

/**
 * Performs query returning aggregated reading values for provided year
 * parameter.
 *
 * @author ivano
 *
 */
public class AggregatedReadingForYear implements MeterReadingOperation {

	private static final String EXCEPTION_MESSAGE = "Couldn't parse provided year parameter. Provide valid year value; eg. 2021";

	@Override
	public Page<QueryResult> view(MeterReadingRepository meterReadingRepository, Pageable pageable, String criteria)
			throws BadRequestException {

		try {
			int year = Integer.parseInt(criteria);
			return meterReadingRepository.getAggregatedReadingForYear(year, pageable);
		} catch (NumberFormatException exception) {
			throw new BadRequestException(EXCEPTION_MESSAGE);
		}
	}
}
