package com.odak.meterreading.helper;

public enum ViewReadingType {

	AGGREGATED("aggr"), YEARLY("yearly"), MONTHLY("monthly");

	private final String value;

	ViewReadingType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
