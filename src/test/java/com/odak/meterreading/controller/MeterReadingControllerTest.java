package com.odak.meterreading.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.odak.meterreading.model.MeterReadingDto;
import com.odak.meterreading.service.MeterReadingService;

@RunWith(SpringJUnit4ClassRunner.class)
public class MeterReadingControllerTest {

	@Mock
	MeterReadingService mockMeterReadingService;
	@InjectMocks
	MeterReadingController meterReadingController;

	@Test
	public void whenAddMeterReading_thenCreate() {
		MeterReadingDto meterReadingDto = new MeterReadingDto();

		meterReadingController.addMeterReading(meterReadingDto);

		verify(mockMeterReadingService, times(1)).create(meterReadingDto);
	}

	@Test
	public void whenGetMeterReading_thenGetMeterReadingById() throws Exception {
		meterReadingController.getMeterReadingById(Long.valueOf(1));

		verify(mockMeterReadingService, times(1)).getMeterReadingById(Long.valueOf(1));
	}

	@Test
	public void whenUpdateMeterReading_thenUpdate() throws Exception {
		meterReadingController.updateMeterReading(Long.valueOf(1), new MeterReadingDto());

		verify(mockMeterReadingService, times(1)).update(Long.valueOf(1), new MeterReadingDto());
	}

	@Test
	public void whenDeleteMeterReading_thenDelete() throws Exception {
		meterReadingController.deleteMeterReading(Long.valueOf(1));

		verify(mockMeterReadingService, times(1)).delete(Long.valueOf(1));
	}

	@Test
	public void whenGetMeterReadings_thenFindAll() throws Exception {
		HashMap<String, String> queryParams = new HashMap<>();

		meterReadingController.getMeterReadings(queryParams);

		verify(mockMeterReadingService, times(1)).findAll(mockMeterReadingService.buildQuery(queryParams));
	}

	@Test
	public void whenGetMeterReadings_withTypeParam_thenQuery() throws Exception {
		HashMap<String, String> queryParams = new HashMap<>();
		queryParams.put("type", "aggr");
		queryParams.put("value", "2021");

		meterReadingController.getMeterReadings(queryParams);

		verify(mockMeterReadingService, times(1)).query(mockMeterReadingService.buildQuery(queryParams));
	}
}
