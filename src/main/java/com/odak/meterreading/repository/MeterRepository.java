package com.odak.meterreading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.odak.meterreading.entity.MeterEntity;

@Repository
public interface MeterRepository extends JpaRepository<MeterEntity, Long> {

	
}
