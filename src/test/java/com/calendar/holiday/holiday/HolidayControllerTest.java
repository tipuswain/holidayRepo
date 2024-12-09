package com.calendar.holiday.holiday;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.calendar.holiday.demo.controller.HolidayController;
import com.calendar.holiday.demo.model.Holiday;
import com.calendar.holiday.demo.service.HolidayService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(HolidayController.class)
public class HolidayControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private HolidayService holidayService;

    @Test
    void addHolidayTest() throws Exception {
        Holiday holiday = new Holiday();
        
        holiday.setCountry("USA");
		holiday.setHolidayName("New Year");
		holiday.setDayOfWeek("Wednesday");
		holiday.setDate("1/1/2025");

        Mockito.when(holidayService.addHoliday(Mockito.any(Holiday.class)))
                .thenThrow(new IllegalArgumentException("Successfully added.."));

        mockMvc.perform(post("/holidays")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(holiday)))
                .andExpect(status().isBadRequest());
              
    }

}
