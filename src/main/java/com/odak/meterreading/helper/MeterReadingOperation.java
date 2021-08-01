package com.odak.meterreading.helper;

import java.util.List;

import com.odak.meterreading.entity.MeterEntity;

public interface MeterReadingOperation {
	List<MeterEntity> view(List<MeterEntity> meterEntityCollection, Integer... options);
}
