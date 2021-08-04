package com.odak.meterreading.helper.query.operation;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.repository.meter.MeterReadingRepository;
import com.odak.meterreading.repository.meter.QueryResult;

/**
 * Performs query returning reading value for provided month in a year.
 *
 * @author ivano
 *
 */
public class ReadingForMonthInYear implements MeterReadingOperation {

	private static final String EXCEPTION_MESSAGE = "Couldn't parse provided year/month parameter. "
			+ "Provide valid year and month values; eg. 7,2021 or 2021,7";

	@Override
	public Page<QueryResult> view(MeterReadingRepository meterReadingRepository, Pageable pageable, String criteria) {

		List<String> criteriaValues = Arrays.asList(criteria.split(","));

		String yearOrNull = criteriaValues.stream().filter(el -> el.matches("\\d{4}")).findFirst().orElse(null);
		String monthOrNull = criteriaValues.stream().filter(el -> el.matches("\\d{1,2}")).findFirst().orElse(null);

		if (yearOrNull == null || monthOrNull == null) {
			throw new BadRequestException(EXCEPTION_MESSAGE);
		}

		try {
			int year = Integer.parseInt(yearOrNull);
			int month = Integer.parseInt(monthOrNull);
			return meterReadingRepository.getReadingForMonthInYear(year, month, pageable);
		} catch (NumberFormatException exception) {
			throw new BadRequestException(EXCEPTION_MESSAGE);
		}
	}
}
