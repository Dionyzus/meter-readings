package com.odak.meterreading.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity describing device.
 * 
 * @author ivano
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Entity
@Table(name = "device")
public class DeviceEntity extends RepresentationModel<DeviceEntity> {

	public DeviceEntity() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	@Column(name = "device_id")
	private Long id;

	@ElementCollection
	@JsonProperty("meter_reading_collection")
	@OneToMany(mappedBy = "device")
	@JsonIgnoreProperties("device")
	private List<MeterReadingEntity> meterReadingCollection;
}
