package com.in.fmc.fmsadminservice.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "FLIGHT")
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer flightId;

	private String flightNumber;

	private String flightName;

	private String flightFare;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "flight")
	private FlightSchedule flightSchedule;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "flight")
	private FlightRoute flightRoute;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "flight", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Passenger> passengers = new ArrayList<>();

}
