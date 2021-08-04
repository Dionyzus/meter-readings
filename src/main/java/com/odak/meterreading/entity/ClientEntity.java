package com.odak.meterreading.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity describing client.
 * @author ivano
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
@TypeDef(name = "json", typeClass = Address.class)
public class ClientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "full_name")
	@NotNull(message = "Full name is required.")
	@JsonProperty("full_name")
	private String fullName;

	@Type(type = "json")
	@NotNull(message = "Address is required.")
	@JsonProperty("address")
	@Embedded
	@AttributeOverrides({
		  @AttributeOverride( name = "streetName", column = @Column(name = "client_street_name")),
		  @AttributeOverride( name = "city", column = @Column(name = "client_city")),
		  @AttributeOverride( name = "postalCode", column = @Column(name = "client_postal_code"))
		})
	private Address address;

	@NotNull(message = "Device id is required.")
	@Column(name = "device_id")
	@JsonProperty("device_id")
	private Long deviceId;
}
