package com.odak.meterreading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odak.meterreading.entity.MeterEntity;

public interface MeterRepository extends JpaRepository<MeterEntity, Long> {
}
