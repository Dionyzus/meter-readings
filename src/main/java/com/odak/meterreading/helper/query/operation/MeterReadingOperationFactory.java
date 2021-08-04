package com.odak.meterreading.helper.query.operation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Factory class invoking meter reading query operation according to provided
 * query type.
 * 
 * @author ivano
 *
 */
public class MeterReadingOperationFactory {

	static Map<String, MeterReadingOperation> operationMap = new HashMap<>();
	static {
		operationMap.put(QueryReadingType.AGGREGATED.toString(), new AggregatedReadingForYear());
		operationMap.put(QueryReadingType.YEARLY.toString(), new ReadingForYear());
		operationMap.put(QueryReadingType.MONTHLY.toString(), new ReadingForMonthInYear());
	}

	public static Optional<MeterReadingOperation> getOperation(String operator) {
		return Optional.ofNullable(operationMap.get(operator));
	}
}
