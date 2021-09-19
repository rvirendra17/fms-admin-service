package com.in.fmc.fmsadminservice.exceptionhandler;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.in.fmc.fmsadminservice.constants.ErrorConstants;
import com.in.fmc.fmsadminservice.constants.ValidationConstants;
import com.in.fmc.fmsadminservice.controllers.FlightController;
import com.in.fmc.fmsadminservice.exceptions.FlightsExistException;
import com.in.fmc.fmsadminservice.models.ErrorResource;
import com.in.fmc.fmsadminservice.models.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(basePackageClasses = { FlightController.class })
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error("Error Occured - {}", ex);

		Set<String> errors = ex.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toSet());

		errors.addAll(ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toSet()));

		String errorCsv = errors.stream().sorted().collect(Collectors.joining(", "));

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		return getErrorResponse(ValidationConstants.INVALID_MISSING_PARAMETERS_MSG + errorCsv, httpStatus, request);
	}

	@ExceptionHandler(FlightsExistException.class)
	private ResponseEntity<Object> handleFlightsExistException(FlightsExistException fex, WebRequest webRequest) {

		log.error("Error occured - {}", fex);
		return getErrorResponse(fex.getMessage(), HttpStatus.NOT_ACCEPTABLE, webRequest);
	}

	private ResponseEntity<Object> getErrorResponse(String message, HttpStatus httpStatus, WebRequest webRequest) {

		String uri = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
		int code = httpStatus.value();
		String title = httpStatus.name();

		ErrorResource errorResource = new ErrorResource(ErrorConstants.RESPONSE_TYPE_DEFINITION, title, code, message,
				uri);
		ErrorResponse errorResponse = new ErrorResponse(errorResource);

		return new ResponseEntity<Object>(errorResponse, httpStatus);

	}
}
