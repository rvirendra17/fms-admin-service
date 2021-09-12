package com.in.fmc.fmsadminservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "FLIGHT_SCHEDULE")
@Data
public class FlightSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer scheduleId;
	private String flightDepartureDate;
	private String flightDepartureTime;
	private String flightArrivalDate;
	private String flightArrivalTime;
	private String flightDuration;

	@OneToOne
	@JoinColumn(nullable = false, name = "fk_flightId")
	@ToString.Exclude
	private Flight flight;

}
