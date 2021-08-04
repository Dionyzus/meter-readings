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

import com.odak.meterreading.entity.DeviceEntity;
import com.odak.meterreading.exception.ResourceNotFoundException;
import com.odak.meterreading.repository.device.DeviceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeviceServiceTest {

	@Mock
	private DeviceRepository deviceRepository;

	@InjectMocks
	private DeviceService deviceService;

	DeviceEntity deviceEntity;

	@Before
	public void setUp() {
		deviceEntity = new DeviceEntity();
		deviceEntity.setId(Long.valueOf(1));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void whenGetMeterReadingsByNull_thenThrowResourceNotFound(){
		when(deviceService.getMeterReadingCollection(null)).thenThrow();
	}

	@Test(expected = ResourceNotFoundException.class)
	public void whenGetByNull_thenThrowResourceNotFound() {
		when(deviceService.getDeviceById(null)).thenThrow();
	}

	@Test
	public void whenFindAll_thenReturnDeviceCollection() {

		List<DeviceEntity> deviceCollection = List.of(deviceEntity);

		when(deviceRepository.findAll()).thenReturn(deviceCollection);

		List<DeviceEntity> devices = deviceService.findAll();

		assertThat(devices.size()).isSameAs(deviceCollection.size());
	}
}
