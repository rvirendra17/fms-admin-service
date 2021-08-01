package com.in.fmc.fmsadminservice.models;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.in.fmc.fmsadminservice.constants.ValidationConstants;
import com.in.fmc.fmsadminservice.dtos.FlightDto;

import lombok.Data;

@Data
public class FlightData {

	@Valid
	@NotNull(message = ValidationConstants.INVALID_FLIGHT_MSG)
	@JsonProperty(value = "flight", required = true)
	private FlightDto flightDto;
}
