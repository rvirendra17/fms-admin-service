package com.in.fmc.fmsadminservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.in.fmc.fmsadminservice.entities.Flight;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Integer> {

	Flight findByFlightNumberAndFlightScheduleFlightDepartureDate(final String fightNumber, final String departureDate);

	Iterable<Flight> findByFlightNumber(final String flightNumber);

}
