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
@Table(name = "PASSENGER")
public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer passengerId;

	private String passengerEmailId;
	private String passengerName;
	private String passengerMobileNo;

	@ManyToOne
	@JoinColumn(name = "fk_flightId", nullable = false)
	private Flight flight;
}
