package com.in.fmc.fmsadminservice.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "FLIGHT_ROUTE")
@Data
public class FlightRoute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer routeId;

	private String flightSource;

	private String flightDestination;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "flightRoute", orphanRemoval = true)
	private List<FlightStop> flightStops = new ArrayList<>();

	@OneToOne
	@JoinColumn(nullable = false, name = "fk_flightId")
	@ToString.Exclude
	private Flight flight;
}
