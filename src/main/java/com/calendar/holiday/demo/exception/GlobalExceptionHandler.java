package com.calendar.holiday.demo.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.calendar.holiday.demo.model.Holiday;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HolidayException.class)
    public ResponseEntity<String> handleHolidayException(HolidayException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handleIOException(IOException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("File processing error: " + ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An unexpected error occurred: " + ex.getMessage());
	}
	 
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    

	public HolidayMapper getHolidayMapper(Holiday savedHoliday) {
		HolidayMapper mapper=new HolidayMapper();
		mapper.setId(savedHoliday.getId());
		mapper.setCountry(savedHoliday.getCountry());
		mapper.setHolidayName(savedHoliday.getHolidayName());
		mapper.setDayOfWeek(savedHoliday.getDayOfWeek());
		mapper.setDate(savedHoliday.getDate());
		return mapper;
	}
	
	public ResponseEntity<HolidayMapper> validateInputRequest(Holiday holiday) {
		HolidayMapper mapper=new HolidayMapper();
		if(!holiday.getCountry().equalsIgnoreCase("USA")) {
			mapper.setErrCode("400");
			mapper.setErrCode("Invalid Input value");
			return new ResponseEntity<>(mapper, HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(mapper, HttpStatus.CREATED);
		}
	}
}

