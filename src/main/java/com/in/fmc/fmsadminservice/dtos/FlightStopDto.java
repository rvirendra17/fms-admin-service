package com.in.fmc.fmsadminservice.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.in.fmc.fmsadminservice.constants.ValidationConstants;

import lombok.Data;

@Data
public class FlightStopDto {

	@NotBlank(message=ValidationConstants.INVALID_FLIGHT_STOPS_CITY_MSG)
	@JsonProperty(value="city",index=1)
	private String stopCity;

	@Max(value=2L, message=ValidationConstants.INVALID_FLIGHT_STOPS_NUMBER_MSG)
	@JsonProperty(value="stop_number",index=0)
	private Integer stopNumber;
}
