package com.odak.meterreading.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.odak.meterreading.entity.MeterReadingEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.helper.query.builder.QueryBuilderImpl.QueryConfiguration;
import com.odak.meterreading.model.MeterReadingDto;
import com.odak.meterreading.service.MeterReadingService;
import com.odak.meterreading.util.string.StringUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Meter reading controller working with {@link MeterReadingEntity}. Contains
 * end-points available to work with meter data.
 *
 * @author ivano
 *
 */
@RestController
@RequestMapping("api/v1")
public class MeterReadingController {

	private MeterReadingService meterReadingService;

	@Autowired
	public MeterReadingController(MeterReadingService meterReadingService) {
		this.meterReadingService = meterReadingService;
	}

	@Operation(summary = "Create new meter reading entry")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Meter reading entry created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MeterReadingEntity.class)) }),
			@ApiResponse(responseCode = "404", description = "Bad request", content = @Content) })
	/**
	 * Creates new meter reading entry for device.
	 * 
	 * @param meterReadingDetails - {@link MeterReadingDto} JSON containing required
	 *                            information for new meter-reading.
	 * @return {@link ResponseEntity} JSON containing added reading for the device.
	 */
	@RequestMapping(value = "/meter-readings", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = "application/json")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<MeterReadingEntity> addMeterReading(
			@Validated @RequestBody MeterReadingDto meterReadingDetails) {

		MeterReadingEntity meterReadingEntity = meterReadingService.create(meterReadingDetails);

		return ResponseEntity.status(HttpStatus.CREATED).body(meterReadingEntity);
	}

	@Operation(summary = "Get all meter readings")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "meter reading collection collection", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MeterReadingEntity.class))) }),
			@ApiResponse(responseCode = "404", description = "Bad query parameters provided", content = @Content) })

	/**
	 * Gets meter-readings for device. If no query parameters are present returns
	 * {@link MeterReadingEntity} collection, otherwise returns data matching query.
	 * 
	 * @param queryParams - list of query parameters used to perform search.
	 *                    parameters are provided as a key=value pair. Example
	 *                    usages:
	 *                    http://localhost:8080/api/v1/meter-readings?type=monthly&value=7,2021
	 *                    http://localhost:8080/api/v1/meter-readings?sortBy=readingValue
	 *                    http://localhost:8080/api/v1/meter-readings
	 * @return {@link ResponseEntity} - JSON containing queried data
	 * @throws BadRequestException - if there is an error in query parameters.
	 */
	@RequestMapping(value = "/meter-readings", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Page<?>> getMeterReadings(@RequestParam(required = false) HashMap<String, String> queryParams)
			throws BadRequestException {

		QueryConfiguration query = meterReadingService.buildQuery(queryParams);

		if (query != null && !StringUtil.isNullOrEmpty(query.type)) {
			return ResponseEntity.ok(meterReadingService.query(query));
		}
		return ResponseEntity.ok(meterReadingService.findAll(query));
	}

	@Operation(summary = "Get meter readings by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "meter reading collection", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MeterReadingEntity.class))) }), })
	/**
	 * Gets meter reading entry.
	 *
	 * @param meterReadingId - meter reading id to search collection with.
	 * @return - {@link ResponseEntity} - JSON containing meter reading entry
	 *         matching provided id, throws exception otherwise.
	 * @throws ResourceNotFoundException - exception if there is no entry matching
	 *                                   provided id.
	 */
	@RequestMapping(value = "/meter-readings/{id}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<MeterReadingEntity> getMeterReadingById(@PathVariable(value = "id") Long meterReadingId)
			throws ResourceNotFoundException {

		MeterReadingEntity meterReadingEntity = meterReadingService.getMeterReadingById(meterReadingId);

		return ResponseEntity.ok(meterReadingEntity);
	}

	@Operation(summary = "Update meter reading entry")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "meter reading entry updated successfuly", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MeterReadingEntity.class)) }),
			@ApiResponse(responseCode = "404", description = "No meter reading with provided id exists", content = @Content) })
	/**
	 * Updates meter reading entry.
	 * 
	 * @param meterEntityId      - meter reading id of an entry to be updated.
	 * @param meterEntityDetails - JSON containing updated values.
	 * @return {@link ResponseEntity} - JSON containing updated
	 *         {@link MeterReadingEntity} data.
	 * @throws ResourceNotFoundException - exception if there is no entry matching
	 *                                   provided id.
	 */
	@RequestMapping(value = "/meter-readings/{id}", method = RequestMethod.PUT, produces = {
			"application/json" }, consumes = "application/json")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<MeterReadingEntity> updateMeterReading(@PathVariable(value = "id") Long meterEntityId,
			@RequestBody MeterReadingDto meterEntityDetails) throws ResourceNotFoundException {

		MeterReadingEntity meterReadingEntity = meterReadingService.update(meterEntityId, meterEntityDetails);

		return ResponseEntity.ok(meterReadingEntity);
	}

	@Operation(summary = "Delete meter reading enty")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "meter reading entry deleted"),
			@ApiResponse(responseCode = "404", description = "No meter reading with provided id exists", content = @Content) })
	/**
	 * Deletes meter reading entry.
	 * 
	 * @param meterEntityId - meter reading id of an entry to be deleted.
	 * @return {@link ResponseEntity} - containing status code if operation was
	 *         successful.
	 * @throws ResourceNotFoundException - exception if there is no entry matching
	 *                                   provided id.
	 */
	@RequestMapping(value = "/meter-readings/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> deleteMeterReading(@PathVariable(value = "id") Long meterEntityId)
			throws ResourceNotFoundException {

		meterReadingService.delete(meterEntityId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
