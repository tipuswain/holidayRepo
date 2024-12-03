package com.calendar.holiday.demo.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class ValidationUtil {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static boolean isValidDate(String dateString) {
		try {
			LocalDate date = LocalDate.parse(dateString, FORMATTER);
			// If parse is successful, it's a valid date
			return true;
		} catch (DateTimeParseException e) {
			// If parsing fails, the date is invalid
			return false;
		}
	}

	public static boolean isValidDay(String day) {
		List<String> strDays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thusday", "Friday", "Saturday",
				"Sunday");

		if (strDays.contains(day)) {

			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isValidCountry(String cu) {
		List<String> country = Arrays.asList("USA", "usa", "CANADA", "canada");

		if (country.contains(cu)) {

			return true;
		} else {
			return false;
		}
	}

}
