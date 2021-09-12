package com.in.fmc.fmsadminservice.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in.fmc.fmsadminservice.entities.Flight;
import com.in.fmc.fmsadminservice.models.Response;
import com.in.fmc.fmsadminservice.repositories.FlightRepository;

@RunWith(value = SpringRunner.class)
public class FlightServiceImplTest {

	@Mock
	private FlightRepository flightRepository;

	private static ResourceLoader resourceLoader;

	private static ObjectMapper objectMapper;

	private static Flight flight;
	private static Collection<Flight> flightsToAdd;
	private static Collection<Flight> flights;
	private static Response successResponse;
	private static Response successWithWarningResponse;

	@InjectMocks
	private FlightServiceImpl flightServiceImpl = new FlightServiceImpl();

	private static String flightPath;
	private static String flightsPath;
	private static String flightsToAddPath;
	private static String successResponsePath;
	private static String successWithWarnigResponsePath;

	@BeforeClass
	public static void init() throws JsonParseException, JsonMappingException, IOException {

		flightPath = "classpath:flights/flight.json";
		flightsPath = "classpath:flights/flights.json";
		flightsToAddPath = "classpath:flights/flightsToAdd.json";
		successResponsePath = "classpath:responses/addFlightSuccessResponse.json";
		successWithWarnigResponsePath = "classpath:responses/addFlightSuccessWithWarningResponse.json";

		resourceLoader = new DefaultResourceLoader();
		objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		flight = objectMapper.readValue(resourceLoader.getResource(flightPath).getFile(), Flight.class);
		flightsToAdd = objectMapper.readValue(resourceLoader.getResource(flightsToAddPath).getFile(),
				new TypeReference<Collection<Flight>>() {
				});
		flights = objectMapper.readValue(resourceLoader.getResource(flightsPath).getFile(),
				new TypeReference<Collection<Flight>>() {
				});
		successResponse = objectMapper.readValue(resourceLoader.getResource(successResponsePath).getFile(),
				Response.class);
		successWithWarningResponse = objectMapper
				.readValue(resourceLoader.getResource(successWithWarnigResponsePath).getFile(), Response.class);
	}

	@Test
	public void addFlightsSuccessTest() throws IOException {

		System.out.println("Flights " + flights);
		System.out.println("Resource " + resourceLoader.getResource(flightsPath));
		System.out.println("Flights Object - " + resourceLoader.getResource(flightsPath).getFile());
		System.out.println("SuccessResponse " + successResponse);
		when(flightRepository.findByFlightNumberAndFlightScheduleFlightDepartureDate(Mockito.anyString(),
				Mockito.anyString())).thenReturn(null);
		when(flightRepository.saveAll(flights)).thenReturn(flights);

		ResponseEntity<Response> expectedResponseEntity = new ResponseEntity<Response>(successResponse,
				HttpStatus.CREATED);

		assertEquals(expectedResponseEntity, flightServiceImpl.addFlights(flights));

	}

	@Test
	public void addFlightsSuccessWithWarningTest() {

		when(flightRepository.findByFlightNumberAndFlightScheduleFlightDepartureDate(flight.getFlightNumber(),
				flight.getFlightSchedule().getFlightDepartureDate())).thenReturn(flight);

		when(flightRepository.saveAll(flightsToAdd)).thenReturn(flightsToAdd);

		ResponseEntity<Response> expectedResponseEntity = new ResponseEntity<Response>(successWithWarningResponse,
				HttpStatus.CREATED);

		assertEquals(expectedResponseEntity, flightServiceImpl.addFlights(flights));

	}
}
