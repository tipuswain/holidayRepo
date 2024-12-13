package com.calendar.holiday.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.calendar.holiday.demo.exception.HolidayMapper;
import com.calendar.holiday.demo.exception.ValidationUtil;
import com.calendar.holiday.demo.model.Holiday;
import com.calendar.holiday.demo.service.HolidayService;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

	private static final Logger logger = LogManager.getLogger(HolidayController.class);

	private final HolidayService holidayService;

	@Autowired
	public HolidayController(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

	@GetMapping
	public List<Holiday> getHolidays(@RequestParam String country) {
		logger.info("Getting Holidays list..");
		return holidayService.listHolidaysByCountry(country);
	}

	@PostMapping
	public ResponseEntity<Object> addHoliday(@RequestBody Holiday holiday) {
		logger.info("Inserting holidays details..");

		HolidayMapper error = ValidationUtil.validateInput(holiday);

		if (error.getErrCode() != null && error.getErrCode().equalsIgnoreCase("400")) {
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		List<Holiday> holidayListByDate = holidayService.listHolidaysDates(holiday.getDate());
		
		if (holidayListByDate.size() > 0) {
			HolidayMapper dupError = ValidationUtil.checkDuplicate(holidayListByDate, holiday);
			if (dupError.getErrCode() != null && dupError.getErrCode().equalsIgnoreCase("400")) {
				return new ResponseEntity<>(dupError, HttpStatus.BAD_REQUEST);
			}
		}

		Holiday savedHoliday = holidayService.addHoliday(holiday);
		return new ResponseEntity<>(savedHoliday, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Holiday> updateHoliday(@PathVariable Long id, @RequestBody Holiday holiday) {
		logger.info("Updating holiday details...");

		Optional<Holiday> updatedHoliday = holidayService.updateHoliday(id, holiday);
		return updatedHoliday.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteHoliday(@PathVariable Long id) {
		logger.info("Deleting Holidays list..");
		if (holidayService.deleteHoliday(id)) {
			return ResponseEntity.ok("Record Deleted successfully.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Record doesn't Exist in System.");
	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		logger.info("File uploading for  Holidays list..");

		if (file.isEmpty() || !file.getOriginalFilename().endsWith(".csv")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid CSV file.");
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			List<Holiday> holidays = new ArrayList<>();
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
			for (CSVRecord record : records) {
				String country = record.get("country");
				String holidayName = record.get("holidayName");
				String dayOfWeek = record.get("dayOfWeek");
				String date = record.get("date");
				if (country.isEmpty() || holidayName.isEmpty() || dayOfWeek.isEmpty() || date.isEmpty()) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Please upload valid input inside file..");
				}
				Holiday holiday = new Holiday();
				holiday.setCountry(country);
				holiday.setHolidayName(holidayName);
				holiday.setDayOfWeek(dayOfWeek);
				holiday.setDate(date);
				holidays.add(holiday);
				holidayService.addHoliday(holiday);
			}
			if (holidays.size() < 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload valid input inside file.");
			}
			return ResponseEntity.ok("File processed successfully. No of records inserted are:" + holidays.size());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}

	}
}
