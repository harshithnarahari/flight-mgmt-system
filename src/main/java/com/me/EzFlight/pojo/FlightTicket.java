package com.me.EzFlight.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FlightTicket {

	@Id
	@GeneratedValue
	@Column(name = "ticket_id", unique = true, nullable = false)
	private long ticket_id;

	@ManyToOne
	@JoinColumn(name = "depF_id")
	private Flight depFlight;
//
	@ManyToOne
	@JoinColumn(name = "retF_id")
	private Flight retFlight;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private int totalPrice;

	private String airline;

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public FlightTicket() {
//		departingFlight = new Flight();
//		returningFlight = new Flight();
	}

	public long getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(long ticket_id) {
		this.ticket_id = ticket_id;
	}

	public Flight getDepFlight() {
		return depFlight;
	}

	public void setDepFlight(Flight depFlight) {
		this.depFlight = depFlight;
	}

	public Flight getRetFlight() {
		return retFlight;
	}

	public void setRetFlight(Flight retFlight) {
		this.retFlight = retFlight;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

}
