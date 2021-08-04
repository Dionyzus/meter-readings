package com.odak.meterreading.repository.device;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odak.meterreading.entity.DeviceEntity;

/**
 * Device repository available for additional persistence methods.
 * Provides basic fetching methods and enables defining custom search methods.
 * @author ivano
 *
 */
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long>{

}
