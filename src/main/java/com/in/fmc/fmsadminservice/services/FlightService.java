package com.in.fmc.fmsadminservice.services;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import com.in.fmc.fmsadminservice.entities.Flight;

public interface FlightService {

	ResponseEntity<String> addFlights(Collection<Flight> flights);
	
}
