package com.odak.meterreading.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MeterDto {

	@JsonProperty("year")
	private String year;
	@JsonProperty("month")
	private String month;
	@JsonProperty("reading_value")
	private Double readingValue;

	public LocalDate convertReadingDate() throws DateTimeParseException {

		StringBuilder date = new StringBuilder("01");
		date.append("-");
		date.append(this.month);
		date.append("-");
		date.append(this.year);

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.parse(date, dateFormat);
	}
}
