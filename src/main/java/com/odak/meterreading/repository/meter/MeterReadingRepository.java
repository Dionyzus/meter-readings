package com.odak.meterreading.repository.meter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.odak.meterreading.entity.MeterReadingEntity;

/**
 * Meter reading repository available for additional persistence methods.
 * Provides basic fetching methods and enables defining custom search methods.
 * 
 * @author ivano
 *
 */
public interface MeterReadingRepository extends JpaRepository<MeterReadingEntity, Long> {

	/**
	 * Finds meter reading entry matching provided date.
	 *
	 * @param date - {@link LocalDate} instance in dd-MM-yyyy format
	 * @return {@link Optional} instance.
	 */
	Optional<MeterReadingEntity> findByReadingTime(LocalDate date);

	/**
	 * Performs collection query to get aggregated meter readings.
	 *
	 * @param year     - year value to query collection with.
	 * @param pageable - {@link Pageable} instance.
	 * @return {@link Page} containing aggregated meter reading value for provided
	 *         year.
	 */
	@Query(value = "SELECT YEAR(reading_time) as \"Year\", SUM(reading_value) as \"Total\" FROM electric_device.meter WHERE YEAR(reading_time)= :year group by year", nativeQuery = true)
	Page<QueryResult> getAggregatedReadingForYear(@Param("year") Integer year, Pageable pageable);

	/**
	 * Performs collection query to get meter readings for a year.
	 *
	 * @param year     - year value to query collection with.
	 * @param pageable - {@link Pageable} instance.
	 * @return {@link Page} containing list of meter reading values for provided
	 *         year.
	 */
	@Query(value = "select year(reading_time) as \"Year\", group_concat( concat(MONTHNAME((reading_time)), '='), reading_value separator ', ') as \"Readings\" from electric_device.meter where year(reading_time)= :year group by year", nativeQuery = true)
	Page<QueryResult> getReadingForYear(@Param("year") Integer year, Pageable pageable);

	/**
	 * Performs collection query to get meter readings for a month in year.
	 * 
	 * @param year     - year value to query collection with.
	 * @param month    - month value to query collection with.
	 * @param pageable - {@link Pageable} instance.
	 * @return {@link Page} containing meter reading for provided year and month.
	 */
	@Query(value = "SELECT YEAR(reading_time) as \"Year\", MONTHNAME(reading_time) as \"Month\", SUM(reading_value) as \"Reading\" FROM electric_device.meter where YEAR(reading_time)= :year and MONTH(reading_time)= :month group by id", nativeQuery = true)
	Page<QueryResult> getReadingForMonthInYear(@Param("year") Integer year, @Param("month") Integer month,
			Pageable pageable);

	/**
	 * Gets all meter readings for device.
	 *
	 * @param deviceId - device id to query collection with.
	 * @return list containing {@link MeterReadingEntity} entries.
	 */
	List<MeterReadingEntity> findAllByDeviceId(Long deviceId);
}
