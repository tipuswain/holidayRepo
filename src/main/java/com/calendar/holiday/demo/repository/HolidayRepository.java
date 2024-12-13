package com.calendar.holiday.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calendar.holiday.demo.model.Holiday;

import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findByCountry(String country);
    List<Holiday> findByDate(String date);
}
