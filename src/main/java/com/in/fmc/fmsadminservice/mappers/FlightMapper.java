package com.in.fmc.fmsadminservice.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

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

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract interface FlightMapper {

	@Mapping(source = "flightScheduleDto", target = "flightSchedule")
	@Mapping(source = "passengerDtos", target = "passengers")
	@Mapping(source = "flightRouteDto", target = "flightRoute")
	Flight mapToFlight(FlightDto flightDto);

	FlightSchedule mapToFlightSchedule(FlightScheduleDto flightScheduleDto);

	@Mapping(source = "flightStopDtos", target = "flightStops")
	FlightRoute mapToFightRoute(FlightRouteDto flightRouteDto);

	List<FlightStop> mapToFlightStops(List<FlightStopDto> flightStopDtos);

	List<Passenger> mapToPassengers(List<PassengerDto> passengerDtos);

}
