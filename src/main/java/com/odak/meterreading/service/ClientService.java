package com.odak.meterreading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.odak.meterreading.entity.Address;
import com.odak.meterreading.entity.ClientEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.repository.client.ClientRepository;

/**
 * Service handling {@link ClientEntity} REST API calls.
 * 
 * @author ivano
 *
 */
public class ClientService {

	private ClientRepository clientRepository;
	private DeviceService deviceService;

	@Autowired
	public ClientService(ClientRepository clientRepository, DeviceService deviceService) {
		this.clientRepository = clientRepository;
		this.deviceService = deviceService;
	}

	/**
	 * Adds new {@link ClientEntity} to collection.
	 *
	 * @param clientEntity - JSON containing required client data.
	 * @return {@link ClientEntity} instance if operation was successful
	 */
	public ClientEntity create(ClientEntity clientEntity) {
		validateEntry(clientEntity);

		return clientRepository.save(clientEntity);
	}

	/**
	 * Gets client collection.
	 * 
	 * @return {@link ClientEntity} collection.
	 */
	public List<ClientEntity> findAll() {
		return clientRepository.findAll();
	}

	/**
	 * Validates provided {@link ClientEntity} data.
	 *
	 * @param clientEntity - client entity instance.
	 */
	private void validateEntry(ClientEntity clientEntity) {

		List<ClientEntity> clientCollection = clientRepository.findAll();
		Long deviceId = clientEntity.getDeviceId();
		Address address = clientEntity.getAddress();

		checkIfAnyClientAtAddress(address, clientCollection);

		checkIfDeviceInUse(deviceId, clientCollection);

		checkIfDeviceExists(deviceId);

	}

	private void checkIfAnyClientAtAddress(Address address, List<ClientEntity> clientCollection) {
		boolean addressMatches = clientCollection.stream().anyMatch(client -> client.getAddress().equals(address));

		if (addressMatches) {
			// This could be 409 conflict as well
			throw new BadRequestException("Client with provided address already exists: " + address.toString());
		}
	}

	private void checkIfDeviceInUse(Long deviceId, List<ClientEntity> clientCollection) {
		boolean deviceIdMatches = clientCollection.stream().anyMatch(client -> client.getDeviceId().equals(deviceId));

		if (deviceIdMatches) {
			// This could be 409 conflict as well
			throw new BadRequestException("Client with provided meter device already exists: " + deviceId);
		}
	}

	private void checkIfDeviceExists(Long deviceId) {
		try {
			deviceService.getDeviceById(deviceId);
		} catch (ResourceNotFoundException exception) {
			throw new BadRequestException("Meter device with provided id does not exist: " + deviceId + " "
					+ " View available meter readers via: http://localhost:8080/api/v1/devices");
		}
	}
}
