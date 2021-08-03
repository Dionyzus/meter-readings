package com.odak.meterreading.helper.query.operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.repository.meter.MeterRepository;
import com.odak.meterreading.repository.meter.QueryResult;

public class AggregatedReading implements MeterReadingOperation {

	private static final String EXCEPTION_MESSAGE = "Couldn't parse provided year parameter. Provide valid year value; eg. 2021";

	@Override
	public Page<QueryResult> view(MeterRepository meterRepository, Pageable pageable, String criteria)
			throws BadRequestException {

		try {
			int year = Integer.parseInt(criteria);
			return meterRepository.getAggregatedReading(year, pageable);
		} catch (NumberFormatException exception) {
			throw new BadRequestException(EXCEPTION_MESSAGE);
		}
	}
}
