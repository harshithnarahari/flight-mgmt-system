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
public class User {
	@Id
	@GeneratedValue
	@Column(name = "user_id", unique = true, nullable = false)
	private long user_id;

	@NotBlank(message = " * Your first name can't be blank")
	@Size(min = 3, max = 15, message = " * Your first name shoud have atleast 3 char")
	@Pattern(regexp = "^[a-zA-Z\s]*$", message = "Your first name can contain only alphabets")
	private String firstName;

	@NotBlank(message = " * Your Last name can't be blank")
	@Size(min = 3, max = 15, message = " * Your Last name shoud have atleast 3 char")
	@Pattern(regexp = "^[a-zA-Z\s]*$", message = "Your first name can contain only alphabets")
	private String lastName;

	private String email;

	@NotBlank(message = " * Your phone number name can't be blank")
	@Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = " * Your phone number has incorrect format")
	private String phone;

	@NotBlank(message = " * Your Address name can't be blank")
	@Size(min = 3, max = 30, message = " * Your Address shoud have atleast 3 char")
	private String address;

	@NotBlank(message = " * Your password can't be blank")
	@Size(min = 3, max = 15, message = " * Your Password shoud have atleast 3 char")
	private String password;
	private String role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<FlightTicket> userTickets;

	public User() {
		userTickets = new ArrayList();

	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
//		userTickets = new ArrayList();
	}

	// Getters and Setter
	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

//
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<FlightTicket> getUserTickets() {
		return userTickets;
	}

	public void setUserTickets(List<FlightTicket> userTickets) {
		this.userTickets = userTickets;
	}

}
