package com.in.fmc.fmsadminservice.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.in.fmc.fmsadminservice.constants.ValidationConstants;
import com.in.fmc.fmsadminservice.dtos.FlightDto;

import lombok.Data;

@Data
public class FlightData {

	@Valid
	@NotEmpty(message = ValidationConstants.INVALID_FLIGHT_MSG)
	@JsonProperty(value = "flights", required = true)
	private List<FlightDto> flightDtos;
}
