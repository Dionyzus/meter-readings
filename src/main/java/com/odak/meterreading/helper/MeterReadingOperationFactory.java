package com.odak.meterreading.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MeterReadingOperationFactory {

	static Map<String, MeterReadingOperation> operationMap = new HashMap<>();
	static {
		operationMap.put(ViewReadingType.AGGREGATED.toString(), new AggregatedYearlyReading());
		operationMap.put(ViewReadingType.YEARLY.toString(), new ReadingPerYear());
		operationMap.put(ViewReadingType.MONTHLY.toString(), new MonthInYearReading());
	}

	public static Optional<MeterReadingOperation> getOperation(String operator) {
		return Optional.ofNullable(operationMap.get(operator));
	}
}
