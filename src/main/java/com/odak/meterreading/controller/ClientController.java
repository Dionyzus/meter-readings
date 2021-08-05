package com.odak.meterreading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.odak.meterreading.entity.ClientEntity;
import com.odak.meterreading.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Client controller working with {@link ClientEntity}. Contains end-points
 * available to work with client data.
 *
 * @author ivano
 *
 */
@RestController
@RequestMapping("api/v1")
public class ClientController {

	private ClientService clientService;

	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@Operation(summary = "Create new client entry")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Client entry created", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ClientEntity.class)) }),
			@ApiResponse(responseCode = "404", description = "Bad request", content = @Content) })
	/**
	 * Creates new client entry.
	 *
	 * @param clientEntityDetails - {@link ClientEntity} instance with required data
	 *                            in JSON format.
	 * @return {@link ResponseEntity} - JSON response containing newly created data
	 *         of type {@link ClientEntity}.
	 */
	@RequestMapping(value = "/clients", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = "application/json")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ClientEntity> addClient(@Validated @RequestBody ClientEntity clientEntityDetails) {

		ClientEntity clientEntity = clientService.create(clientEntityDetails);

		return ResponseEntity.status(HttpStatus.CREATED).body(clientEntity);
	}

	@Operation(summary = "Get all clients")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "client collection", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientEntity.class))) }), })
	/**
	 * Gets {@link ClientEntity} collection.
	 *
	 * @return {@link ResponseEntity} - JSON response containing list of client
	 *         entries.
	 */
	@RequestMapping(value = "/clients", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<List<ClientEntity>> getClients() {

		return ResponseEntity.ok(clientService.findAll());
	}
}
