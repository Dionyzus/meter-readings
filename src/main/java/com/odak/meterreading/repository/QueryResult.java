package com.odak.meterreading.repository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Projection interface to map query result
 * 
 * @author ivano
 *
 */
@JsonInclude(Include.NON_NULL)
public interface QueryResult {
	String getMonth();

	Long getYear();

	Double getReading();

	String getReadings();

	Double getTotal();
}
