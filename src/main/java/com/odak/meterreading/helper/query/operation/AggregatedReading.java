package com.odak.meterreading.helper.query.operation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.odak.meterreading.repository.MeterRepository;
import com.odak.meterreading.repository.QueryResult;

public class AggregatedReading implements MeterReadingOperation {

	@Override
	public Page<QueryResult> view(MeterRepository meterRepository, Pageable pageable, List<String> criteria) {
		return meterRepository.getAggregatedReading(pageable);
	}
}
