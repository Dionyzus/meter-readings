package com.odak.meterreading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.odak.meterreading.entity.ClientEntity;
import com.odak.meterreading.entity.MeterEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.repository.client.ClientRepository;
import com.odak.meterreading.util.jackson.Serialization;

public class ClientService {

	private ClientRepository clientRepository;
	private DeviceService deviceService;

	@Autowired
	public ClientService(ClientRepository clientRepository, DeviceService deviceService) {
		this.clientRepository = clientRepository;
		this.deviceService = deviceService;
	}

	public ClientEntity create(ClientEntity clientEntity) {
		validateEntry(clientEntity);

		JsonNode serialize = Serialization.serialize(clientEntity.getAddress());
		
		return clientRepository.save(clientEntity);
	}

	private void validateEntry(ClientEntity clientEntity) {

		List<ClientEntity> clientCollection = clientRepository.findAll();

		boolean addressMatches = clientCollection.stream()
				.anyMatch(client -> client.getAddress().equals(clientEntity.getAddress()));

		if (addressMatches) {
			// This could be 409 conflict as well
			throw new BadRequestException(
					"Client with provided address already exists: " + clientEntity.getAddress().toString());
		}

		boolean meterIdMatches = clientCollection.stream()
				.anyMatch(client -> client.getDeviceId().equals(clientEntity.getDeviceId()));

		if (meterIdMatches) {
			// This could be 409 conflict as well
			throw new BadRequestException("Client with provided meter device already exists: " + clientEntity.getDeviceId());
		}

		try {
			deviceService.getDeviceById(clientEntity.getDeviceId());
		} catch (ResourceNotFoundException exception) {
			throw new BadRequestException("Meter device with provided id does not exist: " + clientEntity.getDeviceId()
					+ " " + " View available meter readers via: http://localhost:8080/api/v1/devices");
		}

	}

	public List<ClientEntity> findAll() {
		return clientRepository.findAll();
	}
}
