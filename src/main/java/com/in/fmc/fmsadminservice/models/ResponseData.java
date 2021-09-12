package com.in.fmc.fmsadminservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

	private String status;
	private int code;
	private String message;

}
