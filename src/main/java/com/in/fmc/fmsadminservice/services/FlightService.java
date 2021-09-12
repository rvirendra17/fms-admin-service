package com.in.fmc.fmsadminservice.services;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import com.in.fmc.fmsadminservice.entities.Flight;
import com.in.fmc.fmsadminservice.models.Response;

public interface FlightService {

	ResponseEntity<Response> addFlights(Collection<Flight> flights);

}
