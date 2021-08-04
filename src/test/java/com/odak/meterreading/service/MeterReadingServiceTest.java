package com.odak.meterreading.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.odak.meterreading.entity.MeterReadingEntity;
import com.odak.meterreading.exception.BadRequestException;
import com.odak.meterreading.helper.query.builder.QueryBuilderImpl.QueryConfiguration;
import com.odak.meterreading.model.MeterReadingDto;
import com.odak.meterreading.repository.meter.MeterReadingRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class MeterReadingServiceTest {

	@Mock
	private MeterReadingRepository meterReadingRepository;

	@Mock
	private DeviceService deviceService;

	@InjectMocks
	private MeterReadingService meterReadingService;

	MeterReadingDto meterReadingDto;
	MeterReadingEntity meterReadingEntity;

	@Before
	public void setUp() {
		meterReadingDto = new MeterReadingDto();
		meterReadingDto.setDeviceId(Long.valueOf(1));
		meterReadingDto.setMonth("07");
		meterReadingDto.setYear("2021");
		meterReadingDto.setReadingValue(Double.valueOf(100));

		meterReadingEntity = meterReadingService.convertToEntity(meterReadingDto);
	}

	@Test
	public void whenAggrType_thenGetAggregatedReading() {

		QueryConfiguration queryConfig = QueryConfiguration.of("aggr", "2021", 5, 0, "", "");
		meterReadingService.query(queryConfig);

		verify(meterReadingRepository, times(1)).getAggregatedReadingForYear(Integer.valueOf("2021"),
				Pageable.ofSize(5));
	}

	@Test
	public void whenMonthlyType_thenGetReadingForMonthInYear() {

		QueryConfiguration queryConfig = QueryConfiguration.of("monthly", "2021,6", 5, 0, "", "");
		meterReadingService.query(queryConfig);

		verify(meterReadingRepository, times(1)).getReadingForMonthInYear(Integer.valueOf("2021"), Integer.valueOf("6"),
				Pageable.ofSize(5));
	}

	@Test
	public void whenYearlyType_thenGetReadingForYear() {

		QueryConfiguration queryConfig = QueryConfiguration.of("yearly", "2021", 5, 0, "", "");
		meterReadingService.query(queryConfig);

		verify(meterReadingRepository, times(1)).getReadingForYear(Integer.valueOf("2021"), Pageable.ofSize(5));
	}

	@Test(expected = BadRequestException.class)
	public void whenBadParameters_thenThrowBadRequestException() {

		MeterReadingDto meterDto = new MeterReadingDto();
		meterDto.setMonth("05");
		meterDto.setReadingValue(Double.valueOf(50));

		when(meterReadingService.convertToEntity(meterDto)).thenThrow();
	}

	@Test
	public void whenSaveMeterReadingDto_thenReturnMeterReadingEntity() {
		when(meterReadingRepository.save(meterReadingEntity)).thenReturn(meterReadingEntity);

		MeterReadingEntity meterEntity = meterReadingService.create(meterReadingDto);

		assertThat(meterEntity.getReadingValue()).isSameAs(meterReadingEntity.getReadingValue());
	}

}
