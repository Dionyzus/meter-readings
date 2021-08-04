package com.odak.meterreading.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.odak.meterreading.entity.ClientEntity;
import com.odak.meterreading.service.ClientService;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClientControllerTest {

	@Mock
	ClientService mockClientService;
	@InjectMocks
	ClientController clientController;

	@Test
	public void whenAddClient_thenCreateClient() {

		ClientEntity client = new ClientEntity();

		clientController.addClient(client);

		verify(mockClientService, times(1)).create(client);
	}

	@Test
	public void whenGetClients_thenFindAll() throws Exception {
		clientController.getClients();

		verify(mockClientService, times(1)).findAll();
	}
}
