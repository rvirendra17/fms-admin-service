package com.in.fmc.fmsadminservice.dtos;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.in.fmc.fmsadminservice.constants.ValidationConstants;

import lombok.Data;

@Data
public class PassengerDto {

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_PASSENGER_NAME_MSG)
	@JsonProperty(value = "name", index = 0)
	private String passengerName;

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_PASSENGER_EMAIL_MSG)
	@JsonProperty(value = "email", index = 1)
	private String passengerEmailId;

	@NotBlank(message = ValidationConstants.INVALID_FLIGHT_PASSENGER_MOBILE_MSG)
	@JsonProperty(value = "mobile", index = 2)
	private String passengerMobileNo;
}
