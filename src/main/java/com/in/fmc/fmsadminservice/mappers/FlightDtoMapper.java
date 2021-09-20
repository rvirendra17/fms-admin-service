package com.in.fmc.fmsadminservice.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.in.fmc.fmsadminservice.dtos.FlightDto;
import com.in.fmc.fmsadminservice.dtos.FlightRouteDto;
import com.in.fmc.fmsadminservice.dtos.FlightScheduleDto;
import com.in.fmc.fmsadminservice.dtos.FlightStopDto;
import com.in.fmc.fmsadminservice.dtos.PassengerDto;
import com.in.fmc.fmsadminservice.entities.Flight;
import com.in.fmc.fmsadminservice.entities.FlightRoute;
import com.in.fmc.fmsadminservice.entities.FlightSchedule;
import com.in.fmc.fmsadminservice.entities.FlightStop;
import com.in.fmc.fmsadminservice.entities.Passenger;

@Mapper(componentModel="spring")
public interface FlightDtoMapper {

	@Mapping(source="flightSchedule",target="flightScheduleDto")
	@Mapping(source="passengers",target="passengerDtos")
	@Mapping(source="flightRoute",target="flightRouteDto")
	FlightDto mapToFlightDto(Flight flight);

	FlightScheduleDto mapToFlightScheduleDto(FlightSchedule flightSchedule);
	
	@Mapping(source = "flightStops", target = "flightStopDtos")
	FlightRouteDto mapToFlightRouteDto(FlightRoute flightRoute);
	
	List<FlightStopDto> mapToFlightStopDtos(List<FlightStop> flightStops);
	
	List<PassengerDto> mapToPassengerDtos(List<Passenger> passengers);
	
	List<FlightDto> mapToFlightDtos(List<Flight> flights);
}
