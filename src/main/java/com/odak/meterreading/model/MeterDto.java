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

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MeterDto {

	@JsonProperty("year")
	@NotNull(message = "Year value is required.")
	private String year;
	@JsonProperty("month")
	@NotNull(message = "Month value is required.")
	private String month;
	@JsonProperty("reading_value")
	@NotNull(message = "The reading value is required.")
	private Double readingValue;

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
