package com.odak.meterreading.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.odak.meterreading.entity.Address;
import com.odak.meterreading.entity.ClientEntity;
import com.odak.meterreading.repository.client.ClientRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClientServiceTest {

	@Mock
	private ClientRepository clientRepository;

	@Mock
	private DeviceService deviceService;

	@InjectMocks
	private ClientService clientService;

	ClientEntity clientEntity;

	@Before
	public void setUp() {
		clientEntity = new ClientEntity();
		clientEntity.setId(Long.valueOf(1));
		clientEntity.setAddress(new Address("Test Street", "Test City", "10000"));
		clientEntity.setDeviceId(Long.valueOf(1));
		clientEntity.setFullName("Test Name");
	}

	@Test
	public void whenSaveClient_thenReturnClient() {
		when(clientRepository.save(clientEntity)).thenReturn(clientEntity);

		ClientEntity createdClient = clientService.create(clientEntity);

		assertThat(createdClient.getFullName()).isSameAs(clientEntity.getFullName());
	}

	@Test
	public void whenFindAll_thenReturnClientCollection() {

		List<ClientEntity> clientCollection = List.of(clientEntity);

		when(clientRepository.findAll()).thenReturn(clientCollection);

		List<ClientEntity> clients = clientService.findAll();

		assertThat(clients.size()).isSameAs(clientCollection.size());
	}
}
