package com.calendar.holiday.demo.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.calendar.holiday.demo.model.Holiday;

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

	public static HolidayMapper validateInput(Holiday holiday) {
		HolidayMapper error = new HolidayMapper();
		if (holiday.getCountry().isEmpty() || holiday.getDate().isEmpty()
				|| holiday.getDayOfWeek().isEmpty() || holiday.getHolidayName().isEmpty()) {
			error.setErrCode("400");
			error.setErrMessage("Mandatoty fields shouldn't empty.");
		} else if (holiday.getCountry() != null && !ValidationUtil.isValidCountry(holiday.getCountry())) {
			error.setErrCode("400");
			error.setErrMessage("Country should be Either USA or CANADA.");

		}else if (holiday.getDayOfWeek() != null && !ValidationUtil.isValidDay(holiday.getDayOfWeek())) {
			error.setErrCode("400");
			error.setErrMessage("Invalid Day Of Week.");
			// return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

		} else if (holiday.getDate() != null && !ValidationUtil.isValidDate(holiday.getDate())) {
			error.setErrCode("400");
			error.setErrMessage("Invalid date format.");

		} 
		return error;
	}


	public static HolidayMapper checkDuplicate(List<Holiday> holidayListByDate, Holiday holiday) {
		HolidayMapper error = new HolidayMapper();
		for(Holiday check:holidayListByDate) {
			
			if((check.getDate().equalsIgnoreCase(holiday.getDate()) && (check.getCountry().equalsIgnoreCase(holiday.getCountry())))) {
				error.setErrCode("400");
				error.setErrMessage("Holiday is already Exist..");
			}
		}
		return error;
	}

}
