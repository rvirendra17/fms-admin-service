package com.in.fmc.fmsadminservice.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.in.fmc.fmsadminservice.constants.ValidationConstants;

import lombok.Data;

@Data
public class FlightRouteDto {

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_SOURCE_MSG)
	@JsonProperty(value = "source", index = 0)
	private String flightSource;

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_DESTINATION_MSG)
	@JsonProperty(value = "destination", index = 1)
	private String flightDestination;

	@NotNull(message = ValidationConstants.INVALID_FLIGHT_STOPS_MSG)
	@JsonProperty(value = "stops", index = 2, required = true)
	private List<FlightStopDto> flightStopDtos = new ArrayList<>();
}
