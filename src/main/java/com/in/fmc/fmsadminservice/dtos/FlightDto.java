package com.in.fmc.fmsadminservice.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.in.fmc.fmsadminservice.constants.ValidationConstants;

import lombok.Data;

@Data
public class FlightDto {

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_NUMBER_MSG)
	@JsonProperty(value = "number", index = 0)
	private String flightNumber;

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_NAME_MSG)
	@JsonProperty(value = "airline", index = 1)
	private String flightAirlineName;

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_FARE_MSG)
	@JsonProperty(value = "fare", index = 2)
	private String flightFare;

	@Valid
	@NotNull(message = ValidationConstants.INVALID_FLIGHT_SCHEDULE_MSG)
	@JsonProperty(value = "schedule", index = 3)
	private FlightScheduleDto flightScheduleDto;

	@Valid
	@NotNull(message = ValidationConstants.INVALID_FLIGHT_ROUTE_MSG)
	@JsonProperty(value = "route", index = 4)
	private FlightRouteDto flightRouteDto;

	@NotNull(message = ValidationConstants.INVALID_FLIGHT_PASSENGERS_MSG)
	@JsonProperty(value = "passengers", index = 5, required = true)
	private List<PassengerDto> passengerDtos = new ArrayList<PassengerDto>();
}
