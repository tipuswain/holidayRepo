package com.calendar.holiday.demo.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.holiday.demo.model.Holiday;
import com.calendar.holiday.demo.repository.HolidayRepository;



@Service
public class HolidayService {

	@Autowired
	private HolidayRepository holidayRepository;
	
	private static final Logger logger = LogManager.getLogger(HolidayRepository.class);

	
	public List<Holiday> listHolidaysByCountry(String country) {
		logger.info("listing holidays...");
		return holidayRepository.findByCountry(country);
	}
	
	public List<Holiday> listHolidaysDates(String date) {
		logger.info("listing holidays...");
		return holidayRepository.findByDate(date);
	}
	
	public Holiday addHoliday(Holiday holiday) {
		logger.info("adding  holidays...");
		return holidayRepository.save(holiday);
	}

	public Optional<Holiday> updateHoliday(Long id, Holiday holiday) {
		logger.info("updating holidays...");
		if (holidayRepository.existsById(id)) {
			holiday.setId(id);
			return Optional.of(holidayRepository.save(holiday));
		}
		return Optional.empty();
	}

	public boolean deleteHoliday(Long id) {
		logger.info("deleting holidays...");
		if (holidayRepository.existsById(id)) {
			holidayRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
