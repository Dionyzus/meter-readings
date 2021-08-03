package com.odak.meterreading.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.odak.meterreading.entity.MeterEntity;

public interface MeterRepository extends JpaRepository<MeterEntity, Long> {

	@Query(value = "SELECT YEAR(reading_time) as \"Year\", SUM(reading_value) as \"Total\" FROM meter_reading.meter WHERE YEAR(reading_time)=2021", nativeQuery = true)
	Page<QueryResult> getAggregatedReading(Pageable pageable);

	@Query(value = "select year(reading_time) as \"Year\", group_concat( concat(MONTHNAME((reading_time)), '='), reading_value separator ', ') as \"Readings\" from meter_reading.meter where year(reading_time)=2021", nativeQuery = true)
	Page<QueryResult> getYearlyReading(Pageable pageable);

	@Query(value = "SELECT YEAR(reading_time) as \"Year\", MONTHNAME(reading_time) as \"Month\", SUM(reading_value) as \"Reading\" FROM meter_reading.meter where YEAR(reading_time)=2021 and MONTH(reading_time)=7", nativeQuery = true)
	Page<QueryResult> getMonthlyReading(Pageable pageable);
}
