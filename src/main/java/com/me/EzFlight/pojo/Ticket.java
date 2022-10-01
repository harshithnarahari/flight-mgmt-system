package com.me.EzFlight.pojo;

import java.util.Date;

public class Ticket {
	
	private long ticket_id;
	private Date date_of_journey;
	private double price;
	private Location from;
	private Location to;
	private Flight flight;
	
	public Ticket() {
		from = new Location();
		to = new Location();
		flight = new Flight();
	}
	
	// Getters and Setters
	public long getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(long ticket_id) {
		this.ticket_id = ticket_id;
	}
	public Date getDate_of_journey() {
		return date_of_journey;
	}
	public void setDate_of_journey(Date date_of_journey) {
		this.date_of_journey = date_of_journey;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
	
}
