
package com.calendar.holiday.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.calendar.holiday.demo.model.Holiday;
import com.calendar.holiday.demo.repository.HolidayRepository;

@SpringBootTest
public class HolidayServiceTest {

	@Mock
	private HolidayRepository holidayRepository;

	@InjectMocks
	private HolidayService holidayService;

	@Test
	void testAddHoliday() {
		Holiday holiday = new Holiday(null, "USA", "Independence Day", "Monday", "2024-07-04");
		when(holidayRepository.save(holiday))
				.thenReturn(new Holiday(1L, "USA", "Independence Day", "Monday", "2024-07-04"));

		Holiday savedHoliday = holidayService.addHoliday(holiday);
		assertNotNull(savedHoliday.getId());
	}

	@Test
	void testUpdateHoliday() {
		Holiday holiday = new Holiday(1L, "USA", "National Day", "Monday", "2024-07-04");
		when(holidayRepository.existsById(1L)).thenReturn(true);
		when(holidayRepository.save(holiday)).thenReturn(holiday);

		Optional<Holiday> updatedHoliday = holidayService.updateHoliday(1L, holiday);
		assertTrue(updatedHoliday.isPresent());
	}

	@Test
	void testGetHoliday() {
		Holiday holiday = new Holiday(1L, "USA", "National Day", "Monday", "2024-07-04");
		Holiday holiday1 = new Holiday(2L, "USA", "Chrishmas", "Monday", "2024-07-12");
		List<Holiday> holidaysList = new ArrayList<>();
		holidaysList.add(holiday);
		holidaysList.add(holiday1);
		when(holidayRepository.findByCountry("USA")).thenReturn(holidaysList);

		assertTrue(!holidaysList.isEmpty());
	}

	@Test
	void testDeleteHoliday() {
		Holiday holiday = new Holiday(1L, "USA", "National Day", "Monday", "2024-07-04");
		holidayService.deleteHoliday(1L);

	}
	
	@Test
	void testGetHolidayByDate() {
		Holiday holiday = new Holiday(1L, "USA", "National Day", "Monday", "2024-07-04");
		List<Holiday> holidaysList = new ArrayList<>();
		holidaysList.add(holiday);
		when(holidayRepository.findByDate("2024-07-04")).thenReturn(holidaysList);

		assertTrue(!holidaysList.isEmpty());
	}

}
