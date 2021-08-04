package com.odak.meterreading.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.odak.meterreading.service.DeviceService;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeviceControllerTest {

	@Mock
	DeviceService mockDeviceService;
	@InjectMocks
	DeviceController deviceController;

	@Test
	public void whenGetMeterReadings_thenGetMeterReadingCollection() {
		deviceController.getDeviceMeterReadings(Long.valueOf(1));

		verify(mockDeviceService, times(1)).getMeterReadingCollection(Long.valueOf(1));
	}

	@Test
	public void whenGetById_thenGetById() throws Exception {
		deviceController.getDeviceById(Long.valueOf(1));

		verify(mockDeviceService, times(1)).getDeviceById(Long.valueOf(1));
	}

	@Test
	public void whenGetDevices_thenFindAll() throws Exception {
		deviceController.getDevices();

		verify(mockDeviceService, times(1)).findAll();
	}
}
