package com.in.fmc.fmsadminservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

	@JsonProperty(value="result")
	private ResponseData responseData;
}
