package com.me.EzFlight.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Airline {

	@Id
	@GeneratedValue
	@Column(name = "airline_id", unique = true, nullable = false)
	private long airline_id;

	@NotBlank(message = " * Airline name can't be blank")
	@Size(min = 3, max = 15, message = " * Airline Name should have atleast 3 char")
	@Pattern(regexp = "^[a-zA-Z\s]*$", message = "Airline Name can contain only alphabets")
	private String airlineName;
	@OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Flight> flights;

	public Airline() {
		flights = new ArrayList<Flight>();
	}

	public Airline(String airlineName) {
		this.airlineName = airlineName;
	}

	public long getAirline_id() {
		return airline_id;
	}

	public void setAirline_id(long airline_id) {
		this.airline_id = airline_id;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

}
