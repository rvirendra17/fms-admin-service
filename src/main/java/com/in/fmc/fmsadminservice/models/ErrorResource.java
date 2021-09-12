package com.in.fmc.fmsadminservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResource {

	@JsonProperty(value = "reference", index = 0)
	private String type;
	@JsonProperty(value = "title", index = 1)
	private String title;
	@JsonProperty(value = "status", index = 2)
	private int code;
	@JsonProperty(value = "message", index = 3)
	private String details;
	@JsonProperty(value = "path", index = 4)
	private String uri;

}
