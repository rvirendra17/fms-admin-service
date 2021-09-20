package com.in.fmc.fmsadminservice.models;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ResponseData {

	@JsonProperty(value = "status", index = 0)
	private String status;
	@JsonProperty(value = "code", index = 1)
	private int code;
	@JsonProperty(value = "message", index = 2)
	private String message;
	@Nullable
	@JsonProperty(value = "data", index = 3)
	private Object data;

}
