package com.in.fmc.fmsadminservice.exceptionhandler;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.in.fmc.fmsadminservice.constants.ValidationConstants;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Set<String> errors = ex.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toSet());

		errors.addAll(ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toSet()));

		String errorCsv = errors.stream().sorted().collect(Collectors.joining(", "));

		return new ResponseEntity<Object>(ValidationConstants.INVALID_MISSING_PARAMETERS_MSG + errorCsv,
				HttpStatus.BAD_REQUEST);
	}
}
