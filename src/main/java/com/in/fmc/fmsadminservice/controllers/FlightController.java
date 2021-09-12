package com.in.fmc.fmsadminservice.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.fmc.fmsadminservice.mappers.FlightMapper;
import com.in.fmc.fmsadminservice.models.FlightData;
import com.in.fmc.fmsadminservice.models.Response;
import com.in.fmc.fmsadminservice.services.FlightService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
@Validated
public class FlightController {

	@Autowired
	private FlightService flightService;

	@Autowired
	private FlightMapper flightMapper;

	@PostMapping("/flights")
	public ResponseEntity<Response> addFlights(@Valid @RequestBody FlightData flightData) {

		log.info("In addFlights()");
		return flightService.addFlights(flightMapper.mapToFlights(flightData.getFlightDtos()));

	}
}
