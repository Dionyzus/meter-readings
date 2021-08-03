package com.odak.meterreading.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = -2292193634357272031L;

	@JsonProperty("street_name")
	@NotNull(message = "The street name is required")
	private String streetName;

	@JsonProperty("city")
	@NotNull(message = "City value is required.")
	private String city;

	@JsonProperty("postal_code")
	@NotNull(message = "Postal code is required.")
	private String postalCode;
}
