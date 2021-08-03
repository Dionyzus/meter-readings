package com.odak.meterreading.util.string;

/**
 * String utility class, containing useful methods to work with String.
 * 
 * @author ivano
 *
 */
public class StringUtil {

	private StringUtil() {
		throw new UnsupportedOperationException("Utils class instantiation not allowed.");
	}

	/**
	 * Checks if String value is null or empty.
	 *
	 * @param value - String value.
	 * @return true if value is null or empty, false otherwise.
	 */
	public static boolean isNullOrEmpty(String value) {
		if (value == "" || value == null) {
			return true;
		}
		return false;
	}

	/**
	 * Gets integer value from string.
	 *
	 * @param value        - String value representing number.
	 * @param defaultValue - default value if parsing fails.
	 * @return Integer value of a provided string, default value otherwise.
	 */
	public static Integer tryParseInteger(String value, Integer defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException exception) {
			// Missing logger
			return defaultValue;
		}
	}
}
