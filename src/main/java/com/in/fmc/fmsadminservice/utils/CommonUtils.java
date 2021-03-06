package com.in.fmc.fmsadminservice.utils;

import org.springframework.http.HttpStatus;

import com.in.fmc.fmsadminservice.constants.Constants;
import com.in.fmc.fmsadminservice.models.Response;
import com.in.fmc.fmsadminservice.models.ResponseData;

public class CommonUtils {

	public static Response getResponse(String message, HttpStatus httpStatus, Object data) {

		ResponseData responseData = new ResponseData(Constants.SUCCESS, httpStatus.value(), message, data);
		return new Response(responseData);

	}
}
