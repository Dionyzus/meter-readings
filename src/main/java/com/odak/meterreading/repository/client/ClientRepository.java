package com.odak.meterreading.repository.client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odak.meterreading.entity.ClientEntity;
import com.odak.meterreading.entity.DeviceEntity;

/**
 * Client repository available for additional persistence methods. Provides
 * basic fetching methods and enables defining custom search methods.
 *
 * @author ivano
 *
 */
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

	/**
	 * Get optional of device entry
	 * 
	 * @param deviceId - device entry id to query collection with.
	 * @return {@link Optional} of {@link DeviceEntity}.
	 */
	Optional<DeviceEntity> findDeviceByDeviceId(Long deviceId);

}
