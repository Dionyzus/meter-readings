package com.odak.meterreading.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.odak.meterreading.util.jackson.CustomDeviceEntityDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity describing meter-reading.
 * @author ivano
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meter")
public class MeterReadingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	@Column(name = "id")
	private Long id;

	@JsonDeserialize(using = CustomDeviceEntityDeserializer.class)
	@JsonProperty("device")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "device_id", nullable = false)
	@JsonIgnoreProperties("meter_reading_collection")
	private DeviceEntity device;

	@JsonProperty("reading_value")
	@Column(name = "reading_value")
	@NotNull(message = "The reading value is required.")
	private Double readingValue;

	@JsonProperty("reading_time")
	@Column(name = "reading_time")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@NotNull(message = "The reading time is required.")
	private LocalDate readingTime;
}
