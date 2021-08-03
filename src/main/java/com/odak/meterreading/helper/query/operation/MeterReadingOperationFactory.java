package com.odak.meterreading.helper.query.operation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MeterReadingOperationFactory {

	static Map<String, MeterReadingOperation> operationMap = new HashMap<>();
	static {
		operationMap.put(QueryReadingType.AGGREGATED.toString(), new AggregatedReading());
		operationMap.put(QueryReadingType.YEARLY.toString(), new YearlyReading());
		operationMap.put(QueryReadingType.MONTHLY.toString(), new MonthlyReading());
	}

	public static Optional<MeterReadingOperation> getOperation(String operator) {
		return Optional.ofNullable(operationMap.get(operator));
	}
}
