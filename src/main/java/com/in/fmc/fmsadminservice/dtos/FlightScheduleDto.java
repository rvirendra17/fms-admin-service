package com.in.fmc.fmsadminservice.dtos;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.in.fmc.fmsadminservice.constants.ValidationConstants;

import lombok.Data;

@Data
public class FlightScheduleDto {

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_DEPARTURE_DATE_MSG)
	@JsonProperty(value = "departure_date", index = 0)
	private String flightDepartureDate;

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_DEPARTURE_TIME_MSG)
	@JsonProperty(value = "departure_time", index = 1)
	private String flightDepartureTime;

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_ARRIVAL_DATE_MSG)
	@JsonProperty(value = "arrival_date", index = 2)
	private String flightArrivalDate;

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_ARRIVAL_TIME_MSG)
	@JsonProperty(value = "arrival_time", index = 3)
	private String flightArrivalTime;

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_DURATION_MSG)
	@JsonProperty(value = "duration", index = 4)
	private String flightDuration;
}
