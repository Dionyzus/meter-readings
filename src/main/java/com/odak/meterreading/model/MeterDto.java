package com.odak.meterreading.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MeterDto {

	@JsonProperty("date")
	private Date date;
	@JsonProperty("total")
	private Double value;
}
