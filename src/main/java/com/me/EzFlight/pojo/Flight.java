package com.me.EzFlight.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Flight {

	@Id
	@NotNull(message = " * Flight number can't be blank")
	@Min(value = 7011, message = "Minimum Value for Flight Number is 7011")
	@Max(value = 9000, message = "Maximum Value for FLight Number is 9000")
	private long flight_no;

	@NotNull(message = " * Number of Seats can't be blank")
	@Min(value = 50, message = "Minimum number of seats is 50")
	@Max(value = 150, message = "Maximum number of seats is 150")
	private int totalSeats;
	private int availableSeats;

	@ManyToOne
	@JoinColumn(name = "airline_id")
	private Airline airline;

	@NotBlank(message = " * Flight name can't be blank")
	@Size(min = 3, max = 15, message = " * Flight Name should have atleast 3 char")
	@Pattern(regexp = "^[a-zA-Z\s]*$", message = "Flight Name can contain only alphabets")
	private String flightName;

	@NotNull(message = "* Location cannot be empty")
	@ManyToOne
	@JoinColumn(name = "LocFom_id")
	private Location from;

	@NotNull(message = "* Location cannot be empty")
	@ManyToOne
	@JoinColumn(name = "LocTo_id")
	private Location to;

	@NotNull(message = " * Price can't be blank")
	@Min(value = 50, message = "Minimum Price of Ticket is 50$")
	private int amount;

	private String date;

	private String time;

//	@OneToMany(mappedBy = "departingFlight", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<FlightTicket> departingFlightTickets;

	@OneToMany(mappedBy = "depFlight", cascade = CascadeType.ALL)
	private List<FlightTicket> departingFlights;

	@OneToMany(mappedBy = "retFlight", cascade = CascadeType.ALL)
	private List<FlightTicket> returningFlights;
//
//	@OneToMany(mappedBy = "returningFlight", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<FlightTicket> returningFlightTickets;

	public Flight() {
		this.airline = new Airline();
		this.to = new Location();
		this.from = new Location();
		this.airline = new Airline();
		this.availableSeats = totalSeats;
	}

	public Flight(Location from, Location to, Airline airline, long flightNo, String flightName, int totalSeats,
			int amount, String date, String time) {
		this.airline = new Airline();
		this.to = new Location();
		this.from = new Location();
		this.airline = new Airline();
		this.availableSeats = totalSeats;
		this.from = from;
		this.to = to;
		this.airline = airline;
		this.flight_no = flightNo;
		this.flightName = flightName;
		this.totalSeats = totalSeats;
		this.amount = amount;
		this.date = date;
		this.time = time;

	}

	public long getFlight_no() {
		return flight_no;
	}

	public void setFlight_no(long flight_id) {
		this.flight_no = flight_id;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public Location getFrom() {
		return from;
	}

	public void setFrom(Location from) {
		this.from = from;
	}

	public Location getTo() {
		return to;
	}

	public void setTo(Location to) {
		this.to = to;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<FlightTicket> getDepartingFlights() {
		return departingFlights;
	}

	public void setDepartingFlights(List<FlightTicket> departingFlights) {
		this.departingFlights = departingFlights;
	}

	public List<FlightTicket> getReturningFlights() {
		return returningFlights;
	}

	public void setReturningFlights(List<FlightTicket> returningFlights) {
		this.returningFlights = returningFlights;
	}

//
}
