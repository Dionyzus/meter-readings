package com.odak.meterreading.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object containing data to add new meter reading entry.
 * 
 * @author ivano
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MeterReadingDto {

	@JsonProperty("device_id")
	@NotNull(message = "Device id is required.")
	private Long deviceId;
	@JsonProperty("year")
	@NotNull(message = "Year value is required.")
	private String year;
	@JsonProperty("month")
	@NotNull(message = "Month value is required.")
	private String month;
	@JsonProperty("reading_value")
	@NotNull(message = "The reading value is required.")
	private Double readingValue;

	/**
	 * Converts provided year and month value to local date format. Usage: eg. 01
	 * for month and 2021 for year.
	 * 
	 * @return {@link LocalDate} instance, throws exception otherwise.
	 * @throws DateTimeParseException - if parsing of provided year and month fails.
	 */
	public LocalDate convertToLocalDate() throws DateTimeParseException {

		StringBuilder date = new StringBuilder("01");
		date.append("-");
		date.append(this.month);
		date.append("-");
		date.append(this.year);

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.parse(date, dateFormat);
	}
}
