package com.odak.meterreading.repository.device;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odak.meterreading.entity.DeviceEntity;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long>{

}
