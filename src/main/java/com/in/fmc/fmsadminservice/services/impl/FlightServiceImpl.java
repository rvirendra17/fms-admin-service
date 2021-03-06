package com.in.fmc.fmsadminservice.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.in.fmc.fmsadminservice.constants.Constants;
import com.in.fmc.fmsadminservice.constants.ErrorConstants;
import com.in.fmc.fmsadminservice.entities.Flight;
import com.in.fmc.fmsadminservice.exceptions.FlightsExistException;
import com.in.fmc.fmsadminservice.exceptions.FlightsNotAddedException;
import com.in.fmc.fmsadminservice.exceptions.FlightsNotFoundException;
import com.in.fmc.fmsadminservice.mappers.FlightDtoMapper;
import com.in.fmc.fmsadminservice.models.Response;
import com.in.fmc.fmsadminservice.repositories.FlightRepository;
import com.in.fmc.fmsadminservice.services.FlightService;
import com.in.fmc.fmsadminservice.utils.CommonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ADMIN
 *
 */
@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private FlightDtoMapper flightDtoMapper;

	@Override
	public ResponseEntity<Response> addFlights(Collection<Flight> flights) {

		Response response;
		HttpStatus httpStatus = HttpStatus.CREATED;

		List<Flight> existingFlights = getExistingFlights(flights);
		if (existingFlights != null && existingFlights.size() > 0) {

			log.info("Existing Flights - {}", existingFlights);

			String existingFlightNumbers = existingFlights.stream().map(flight -> flight.getFlightNumber()).sorted()
					.collect(Collectors.joining(", "));

			List<Flight> flightsToAdd = getFlightsToAdd(existingFlights, flights);

			if (flightsToAdd != null && flightsToAdd.size() > 0) {

				log.info("Flights to add - {}", flightsToAdd);

				saveFlights(flightsToAdd);

				response = CommonUtils.getResponse(Constants.FLIGHTS_ADDED_WITH_WARNING + existingFlightNumbers,
						httpStatus, null);
				return new ResponseEntity<Response>(response, httpStatus);
			} else if (flightsToAdd != null && flightsToAdd.isEmpty()) {

				throw new FlightsExistException(ErrorConstants.FLIGHTS_EXIST_EXCEPTION_MSG);
			}

		}

		saveFlights(flights);
		response = CommonUtils.getResponse(Constants.FLIGHTS_ADDED_SUCCESSFULLY, httpStatus, null);
		return new ResponseEntity<Response>(response, httpStatus);
	}

	private List<Flight> getExistingFlights(Collection<Flight> flights) {

		return flights.stream()
				.map(flight -> flightRepository.findByFlightNumberAndFlightScheduleFlightDepartureDate(
						flight.getFlightNumber(), flight.getFlightSchedule().getFlightDepartureDate()))
				.filter(flight -> flight != null).collect(Collectors.toList());
	}

	private List<Flight> getFlightsToAdd(List<Flight> existingFlights, Collection<Flight> flights) {

		Set<String> existingFlightNumbers = existingFlights.stream().map(flight -> flight.getFlightNumber())
				.collect(Collectors.toSet());

		log.info("Flights already exist: {}, for departure date: {}", existingFlightNumbers,
				existingFlights.get(0).getFlightSchedule().getFlightDepartureDate());

		return flights.stream().filter(fight -> !existingFlightNumbers.contains(fight.getFlightNumber()))
				.collect(Collectors.toList());

	}

	/**
	 * @author -ADMIN
	 * @param - List<Flights>
	 * @Details - Sets parent entities to children entities and save all entities to
	 *          DB
	 * 
	 */

	private void saveFlights(Collection<Flight> flights) {

		for (Flight flight : flights) {

			flight.getFlightSchedule().setFlight(flight);
			flight.getFlightRoute().setFlight(flight);
			flight.getPassengers().stream().forEach(passenger -> passenger.setFlight(flight));
			flight.getFlightRoute().getFlightStops().stream()
					.forEach(stop -> stop.setFlightRoute(flight.getFlightRoute()));

		}

		flightRepository.saveAll(flights);
		log.info("Flights saved - {}", flights);
	}

	@Override
	public ResponseEntity<Response> getFlights(Set<String> flightNumbers) {

		List<Flight> flights = (List<Flight>) flightRepository.findAll();
		Response response = null;
		HttpStatus httpStatus = HttpStatus.OK;

		if (flights.isEmpty()) {
			throw new FlightsNotAddedException(ErrorConstants.FLIGHTS_NOT_ADDED_EXCEPTION_MSG);

		} else if (flightNumbers.isEmpty() && !flights.isEmpty()) {

			response = CommonUtils.getResponse(Constants.FLIGHTS_FOUND_SUCCESSFULLY, httpStatus,
					flightDtoMapper.mapToFlightDtos(flights));
			return new ResponseEntity<Response>(response, httpStatus);

		} else if (!flightNumbers.isEmpty()) {

			List<Flight> foundFlights = flights.stream()
					.filter(flight -> flightNumbers.contains(flight.getFlightNumber())).collect(Collectors.toList());

			if (flightNumbers.size() != foundFlights.size() && foundFlights.size() > 0) {

				log.info("Flight numbers set size: {}, Found flights list size: {}", flightNumbers.size(),
						foundFlights.size());

				Set<String> foundFlightNumbers = foundFlights.stream().map(flight -> flight.getFlightNumber())
						.collect(Collectors.toSet());

				log.info("Found flight numbers- {}", foundFlightNumbers);

				String notFoundFlightNumbers = flightNumbers.stream()
						.filter(number -> !foundFlightNumbers.contains(number)).collect(Collectors.joining(", "));

				response = CommonUtils.getResponse(
						Constants.FLIGHTS_FOUND_SUCCESSFULLY_WITH_WARNING + notFoundFlightNumbers, httpStatus,
						flightDtoMapper.mapToFlightDtos(foundFlights));

				return new ResponseEntity<Response>(response, httpStatus);

			} else if (flightNumbers.size() == foundFlights.size()) {

				log.info("Flight number size: {}, Found flights size: {}", flightNumbers.size(), foundFlights.size());

				response = CommonUtils.getResponse(Constants.FLIGHTS_FOUND_SUCCESSFULLY, httpStatus,
						flightDtoMapper.mapToFlightDtos(foundFlights));

				return new ResponseEntity<Response>(response, httpStatus);
			} else if (foundFlights.isEmpty()) {

				throw new FlightsNotFoundException(ErrorConstants.FLIGHTS_NOT_FOUND_EXCEPTION_MSG);
			}

		}
		return null;
	}
}
