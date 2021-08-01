package com.odak.meterreading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.odak.meterreading.entity.MeterReadingEntity;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReadingEntity, Long> {

	
}
