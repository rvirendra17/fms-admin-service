package com.in.fmc.fmsadminservice.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.in.fmc.fmsadminservice.entities.Flight;
import com.in.fmc.fmsadminservice.repositories.FlightRepository;
import com.in.fmc.fmsadminservice.services.FlightService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepository;

	@Override
	public ResponseEntity<String> addFlights(Collection<Flight> flights) {

		List<Flight> existingFlights = getExistingFlights(flights);
		if (existingFlights != null && existingFlights.size() > 0) {

			String existingFlightNumbers = existingFlights.stream().map(flight -> flight.getFlightNumber()).sorted()
					.collect(Collectors.joining(", "));

			log.info("Flights already exists: {}, for departure date: {}", existingFlightNumbers,
					existingFlights.get(0).getFlightSchedule().getFlightDepartureDate());

			flights.removeAll(existingFlights);
			if (flights != null && flights.size() > 0) {

				flightRepository.saveAll(flights);
				return new ResponseEntity<String>("Warning - Flights added except - " + existingFlightNumbers,
						HttpStatus.CREATED);
			} else if (flights != null && flights.isEmpty()) {
				return new ResponseEntity<String>("Flights already added", HttpStatus.NOT_ACCEPTABLE);
			}

		}
		
		flightRepository.saveAll(flights);
		return new ResponseEntity<String>("Flights added succesfully", HttpStatus.CREATED);
	}

	private List<Flight> getExistingFlights(Collection<Flight> flights) {

		return flights.stream()
				.map(flight -> flightRepository.findByFlightNumberAndFlightScheduleFlightDepartureDate(
						flight.getFlightNumber(), flight.getFlightSchedule().getFlightDepartureDate()))
				.filter(flight -> flight != null)
				.collect(Collectors.toList());
	}
}
