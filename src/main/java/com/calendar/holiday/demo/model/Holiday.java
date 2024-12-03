package com.calendar.holiday.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Holiday {

	@Id
	@Column
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@NotNull(message = "Country cannot be null")
	private String country; // e.g., "USA" or "Canada"
	@Column
	@NotNull
	private String holidayName;
	@Column
	@NotNull
	private String dayOfWeek;
	@Column
	@NotNull
	private String date;

	public Holiday() {
		super();
	}

	public Holiday(Long id, String country, String holidayName, String dayOfWeek, String date) {
		super();
		this.id = id;
		this.country = country;
		this.holidayName = holidayName;
		this.dayOfWeek = dayOfWeek;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
