package com.odak.meterreading.helper.query.operation;

public enum QueryReadingType {

	AGGREGATED("aggr"), YEARLY("yearly"), MONTHLY("monthly");

	private final String value;

	QueryReadingType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
