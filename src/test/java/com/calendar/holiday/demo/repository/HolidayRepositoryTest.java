
package com.calendar.holiday.demo.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.calendar.holiday.demo.model.Holiday;

@SpringBootTest
public class HolidayRepositoryTest {

	@Mock
	private HolidayRepository holidayRepository;

	

	@Test
	void testFindByCountry() {
		Holiday holiday = new Holiday(1L, "USA", "National Day", "Monday", "2024-07-04");
		Holiday holiday1 = new Holiday(2L, "USA", "Chrishmas", "Monday", "2024-07-12");
		List<Holiday> holidaysList = new ArrayList<>();
		holidaysList.add(holiday);
		holidaysList.add(holiday1);
		when(holidayRepository.findByCountry("USA")).thenReturn(holidaysList);

		assertTrue(!holidaysList.isEmpty());
	}

	
	
	@Test
	void testFindByDate() {
		Holiday holiday = new Holiday(1L, "USA", "National Day", "Monday", "2024-07-04");
		List<Holiday> holidaysList = new ArrayList<>();
		holidaysList.add(holiday);
		when(holidayRepository.findByDate("2024-07-04")).thenReturn(holidaysList);

		assertTrue(!holidaysList.isEmpty());
	}

}
