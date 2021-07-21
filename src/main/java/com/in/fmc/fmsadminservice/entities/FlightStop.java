package com.in.fmc.fmsadminservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "FLIGHT_STOP")
public class FlightStop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer stopId;

	private Integer stopNumber;
	private String stopCity;

	@ManyToOne
	@JoinColumn(name = "fk_routeId", nullable = false)
	private FlightRoute flightRoute;
}
