package com.odak.meterreading.helper.query.operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.repository.meter.MeterRepository;
import com.odak.meterreading.repository.meter.QueryResult;

public interface MeterReadingOperation {
	Page<QueryResult> view(MeterRepository meterRepository, Pageable pageable, String criteria) throws BadRequestException;
}
