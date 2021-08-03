package com.odak.meterreading.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.odak.meterreading.entity.MeterEntity;

public interface MeterRepository extends JpaRepository<MeterEntity, Long> {

	Optional<MeterEntity> findByReadingTime(LocalDate date);

	@Query(value = "SELECT YEAR(reading_time) as \"Year\", SUM(reading_value) as \"Total\" FROM meter_reading.meter WHERE YEAR(reading_time)= :year", nativeQuery = true)
	Page<QueryResult> getAggregatedReading(@Param("year") Integer year, Pageable pageable);

	@Query(value = "select year(reading_time) as \"Year\", group_concat( concat(MONTHNAME((reading_time)), '='), reading_value separator ', ') as \"Readings\" from meter_reading.meter where year(reading_time)= :year", nativeQuery = true)
	Page<QueryResult> getYearlyReading(@Param("year") Integer year, Pageable pageable);

	@Query(value = "SELECT YEAR(reading_time) as \"Year\", MONTHNAME(reading_time) as \"Month\", SUM(reading_value) as \"Reading\" FROM meter_reading.meter where YEAR(reading_time)= :year and MONTH(reading_time)= :month", nativeQuery = true)
	Page<QueryResult> getMonthlyReading(@Param("year") Integer year, @Param("month") Integer month, Pageable pageable);
}
