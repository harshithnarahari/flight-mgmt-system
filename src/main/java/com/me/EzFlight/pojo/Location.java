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
public class Location {
	@Id
	@GeneratedValue
	@Column(name = "location_id", unique = true, nullable = false)
	private long location_id;

	@NotBlank(message = " * Location name can't be blank")
	@Size(min = 3, max = 15, message = " * Location Name should have atleast 3 char")
	@Pattern(regexp = "^[a-zA-Z\s]*$", message = "Location Name can contain only alphabets")
	private String locationName;

	@OneToMany(mappedBy = "from", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Flight> fromFlights;

	@OneToMany(mappedBy = "to", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Flight> toFlights;

	public Location() {
		fromFlights = new ArrayList<Flight>();
		toFlights = new ArrayList<Flight>();

	}

	public Location(String locationName) {
		this.locationName = locationName;
	}

	public long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(long location_id) {
		this.location_id = location_id;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public List<Flight> getFromFlights() {
		return fromFlights;
	}

	public void setFromFlights(List<Flight> fromFlights) {
		this.fromFlights = fromFlights;
	}

	public List<Flight> getToFlights() {
		return toFlights;
	}

	public void setToFlights(List<Flight> toFlights) {
		this.toFlights = toFlights;
	}

}
