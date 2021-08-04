package com.in.fmc.fmsadminservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseData {

	private String status;
	private int code;
	private String message;

}
