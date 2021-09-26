package com.in.fmc.fmsadminservice.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.in.fmc.fmsadminservice.constants.Constants;
import com.in.fmc.fmsadminservice.entities.Flight;
import com.in.fmc.fmsadminservice.exceptions.FlightsExistException;
import com.in.fmc.fmsadminservice.exceptions.FlightsNotAddedException;
import com.in.fmc.fmsadminservice.exceptions.FlightsNotFoundException;
import com.in.fmc.fmsadminservice.mappers.FlightDtoMapper;
import com.in.fmc.fmsadminservice.models.Response;
import com.in.fmc.fmsadminservice.repositories.FlightRepository;
import com.in.fmc.fmsadminservice.utils.CommonUtils;

@RunWith(value = SpringRunner.class)
public class FlightServiceImplTest {

	private static ResourceLoader resourceLoader;
	private static ObjectMapper objectMapper;

	private static Flight flight;
	private static Collection<Flight> flightsToAdd;
	private static Collection<Flight> flights;
	private static Response successResponse;
	private static Response successWithWarningResponse;

	private static String flightPath;
	private static String flightsPath;
	private static String flightsToAddPath;
	private static String successResponsePath;
	private static String successWithWarnigResponsePath;

	@Mock
	private FlightDtoMapper flightDtoMapper;

	@Mock
	private FlightRepository flightRepository;

	@InjectMocks
	private FlightServiceImpl flightServiceImpl = new FlightServiceImpl();

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

	@Test(expected = FlightsExistException.class)
	public void addFlights_FlightsExistExceptionTest() {

		when(flightRepository.findByFlightNumberAndFlightScheduleFlightDepartureDate(Mockito.anyString(),
				Mockito.anyString())).thenReturn(flight);
		List<Flight> flights1 = new ArrayList<>();
		flights1.add(flight);
		flightServiceImpl.addFlights(flights1);

	}

	@Test(expected = FlightsNotAddedException.class)
	public void getFlights_whenFlightNumbersEmpty_AndFlightsEmpty() {

		when(flightRepository.findAll()).thenReturn(new ArrayList<>());
		flightServiceImpl.getFlights(new HashSet<String>());
	}

	@Test
	public void getFlights_whenFlightNumbersEmpty_FlightsNotEmpty() {

		when(flightRepository.findAll()).thenReturn(flights);
		HttpStatus httpStatus = HttpStatus.OK;
		Response expectedResponse = CommonUtils.getResponse(Constants.FLIGHTS_FOUND_SUCCESSFULLY, httpStatus,
				flightDtoMapper.mapToFlightDtos(new ArrayList<>(flights)));

		ResponseEntity<Response> expectedResponseEntity = new ResponseEntity<>(expectedResponse, httpStatus);

		assertEquals(expectedResponseEntity, flightServiceImpl.getFlights(new HashSet<>()));
	}

	@Test
	public void getFlights_WhenFlightNumberNotEmpty_AndFewFlightsMismatch() {

		when(flightRepository.findAll()).thenReturn(flights);

		HttpStatus httpStatus = HttpStatus.OK;

		Set<String> flightNumbers = new HashSet<>();
		flightNumbers.add("AI-786");
		flightNumbers.add("I5-744");
		flightNumbers.add("SG-441");

		List<Flight> foundFlights = new ArrayList<>();
		foundFlights.add(flight);

		String notFoundFlightNumbers = "AI-786".concat(", ").concat("SG-441");

		Response expectedResponse = CommonUtils.getResponse(
				Constants.FLIGHTS_FOUND_SUCCESSFULLY_WITH_WARNING + notFoundFlightNumbers, httpStatus,
				flightDtoMapper.mapToFlightDtos(foundFlights));
		ResponseEntity<Response> expectedResponseEntity = new ResponseEntity<>(expectedResponse, httpStatus);

		assertEquals(expectedResponseEntity, flightServiceImpl.getFlights(flightNumbers));

	}

	@Test
	public void getFlights_whenFlightNumbersNotEmpty_AndAllFlightsMatched() {

		when(flightRepository.findAll()).thenReturn(flights);

		HttpStatus httpStatus = HttpStatus.OK;

		Set<String> flightNumbers = new HashSet<>();
		flightNumbers.add("IG-172");
		flightNumbers.add("I5-744");

		List<Flight> foundFlights = new ArrayList<>(flights);

		Response expectedResponse = CommonUtils.getResponse(Constants.FLIGHTS_FOUND_SUCCESSFULLY, httpStatus,
				flightDtoMapper.mapToFlightDtos(foundFlights));

		ResponseEntity<Response> expectedResponseEntity = new ResponseEntity<>(expectedResponse, httpStatus);

		assertEquals(expectedResponseEntity, flightServiceImpl.getFlights(flightNumbers));

	}

	@Test(expected = FlightsNotFoundException.class)
	public void getFlights_WhenFlightNumberNotEmpty_AndNoFlightsMatch() {

		when(flightRepository.findAll()).thenReturn(flights);

		Set<String> flightNumbers = new HashSet<>();
		flightNumbers.add("AI-786");
		flightNumbers.add("SG-441");

		flightServiceImpl.getFlights(flightNumbers);

	}

}
