package com.me.EzFlight.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.Interceptor.MyInterceptor;
import com.me.EzFlight.dao.FlightDAO;
import com.me.EzFlight.dao.FlightTicketDAO;
import com.me.EzFlight.dao.LocationDAO;
import com.me.EzFlight.pojo.Flight;
import com.me.EzFlight.pojo.FlightTicket;
import com.me.EzFlight.pojo.Location;
import com.me.EzFlight.pojo.User;

@Controller
public class BookingController {

	@Autowired
	MyInterceptor interceptor;

	@Autowired
	private JavaMailSender mailSender;

	@PostMapping("fromCitieslist.htm")
	public void ajaxForCities(HttpServletRequest request, HttpServletResponse response, FlightDAO fdao)
			throws IOException {

		try {
			String fromCities = request.getParameter("fromCities");
			PrintWriter out = response.getWriter();

			List list = fdao.listCities(fromCities);

			List<Location> destinations = fdao.listCities(fromCities);

			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < destinations.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("cityname", destinations.get(i).getLocationName());
				jsonArray.put(obj);
			}

			JSONObject Obj = new JSONObject();
			Obj.put("list", jsonArray);
			out.println(Obj);

		} catch (Exception e) {
			System.out.println("Exception in listing destinations" + e.getMessage());
		}
	}

	@PostMapping("listflights.htm")
	public String listFlights(ModelMap model, FlightTicket flightTicket, HttpServletRequest request,
			HttpServletResponse response, FlightDAO flightdao, LocationDAO locationdao)
			throws FlightException, IOException, ParseException {

		if (!interceptor.userIntercept(request, response)) {
			return "user-login";
		}
		HttpSession session = request.getSession();
		flightTicket = new FlightTicket();
		String from = request.getParameter("from");
		String to = request.getParameter("to");

		String departureDate = request.getParameter("departureDate");
		String returnDate = request.getParameter("returnDate");

		if (departureDate.isEmpty() || returnDate.isEmpty() || from == null || to == null) {
			List locationList = locationdao.list();
			model.addAttribute("locationList", locationList);
			request.setAttribute("errMsg", "Please Enter all the fields");
			return "userHome";
		}

		Location source = (Location) locationdao.searchLocationByName(from);
		Location destination = (Location) locationdao.searchLocationByName(to);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date start = formatter.parse(departureDate);
		Date end = formatter.parse(returnDate);

		if (start.after(end)) {
			List locationList = locationdao.list();
			model.addAttribute("locationList", locationList);
			request.setAttribute("bookingErr", "Invalid Dates");
			return "userHome";
		}

		List<Flight> fromSflights = new ArrayList<Flight>();
		List<Flight> fromDflights = new ArrayList<Flight>();

		List<Flight> cnfSDflights = new ArrayList<Flight>();
		List<Flight> cnfDSflights = new ArrayList<Flight>();

		// All the flights from Chennai
		fromSflights = source.getFromFlights();

		// All the Flights from Banglore
		fromDflights = destination.getFromFlights();

		if (fromSflights.size() == 0) {
			request.setAttribute("errMsgSrc", "There are no flights on this date");
		}

		for (Flight sourceFlight : fromSflights) {
			// for each Chennai flight, checking if destination is Banglore (to);
			if (sourceFlight.getTo().getLocationName().equals(to)) {

				if (sourceFlight.getDate().equals(departureDate)) {
					cnfSDflights.add(sourceFlight);
				}

			}
		}

		for (Flight destFlight : fromDflights) {
			// for each banglore flight, checking if destination is chennai
			if (destFlight.getTo().getLocationName().equals(from)) {

				if (destFlight.getDate().equals(returnDate)) {
					cnfDSflights.add(destFlight);
				}

			}
		}

		session.setAttribute("source", from);
		session.setAttribute("destination", to);
		session.setAttribute("srcToDest", cnfSDflights);
		session.setAttribute("destToSrc", cnfDSflights);
		session.setAttribute("flightTicket", flightTicket);
		return "listFlights";

	}

	@PostMapping("bookFlight.htm")
	public String bookFlightHandler(@ModelAttribute("flightTicket") FlightTicket flightTicket, BindingResult result,
			SessionStatus status, HttpServletRequest request, HttpServletResponse response, FlightDAO fdao,
			FlightTicketDAO flightTicketdao) throws FlightException, IOException {
		if (!interceptor.userIntercept(request, response)) {
			response.sendRedirect("/EzFlight/login.htm");
		}
		HttpSession session = request.getSession();
		List<Flight> fromDflights = (List<Flight>) session.getAttribute("destToSrc");
		if (fromDflights.size() == 0) {
			request.setAttribute("errMsgDest", "There are no flights on this date");
		}

		flightTicket = (FlightTicket) session.getAttribute("flightTicket");
		String action = request.getParameter("action");
		if (action.equals("departingFlight")) {
			long flight_no = Long.parseLong(request.getParameter("flightNumber"));
			Flight departingFlight = fdao.searchFlightById(flight_no);
			flightTicket.setDepFlight(departingFlight);
			return "listReturnFlights";
		}

		else if (action.equalsIgnoreCase("returnFlight")) {
			long flight_no = Long.parseLong(request.getParameter("flightNumber"));
			Flight returnFlight = fdao.searchFlightById(flight_no);
			flightTicket.setRetFlight(returnFlight);
			int totalPrice = flightTicket.getDepFlight().getAmount() + flightTicket.getRetFlight().getAmount();
			flightTicket.setTotalPrice(totalPrice);
			return "bookingSummary";
		} else {
			User user = (User) session.getAttribute("user");
			flightTicket.setUser(user);
			flightTicketdao.create(flightTicket);
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(user.getEmail());
			email.setSubject("Ticket Confirmation!");
			email.setText("Hello " + user.getFirstName() + "," + "\n" + "Your booking from "
					+ flightTicket.getDepFlight().getFrom().getLocationName() + " to "
					+ flightTicket.getDepFlight().getTo().getLocationName() + " and "
					+ flightTicket.getRetFlight().getFrom().getLocationName() + " to "
					+ flightTicket.getRetFlight().getTo().getLocationName() + " has been Confirmed!!");

			mailSender.send(email);
			return "bookingSuccess";
		}

	}

	@GetMapping("bookFlight.htm")
	public String bookFlightGetHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (!interceptor.userIntercept(request, response)) {
			return "user-login";
		}
		return "userHome";
	}

	@GetMapping("listflights.htm")
	public String listFlightsGetHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!interceptor.userIntercept(request, response)) {
			return "user-login";
		}
		return "userHome";
	}

	@GetMapping("manageBookings.htm")
	public String manageBooking(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			FlightTicketDAO flightticketdao) throws FlightException, IOException {
		HttpSession session = request.getSession();
		if (!interceptor.userIntercept(request, response)) {
			return "user-login";
		}

		String action = request.getParameter("action");
		if (action.equals("options")) {
			return "manageBookings";
		} else if (action.equals("view")) {
			User currentUser = (User) session.getAttribute("user");
			List bookings = flightticketdao.listFlightsByUser(currentUser.getUser_id());
			System.out.println(bookings.size());
			request.setAttribute("bookings", bookings);
			return "viewBookings";

		} else if (action.equals("delete")) {
			User currentUser = (User) session.getAttribute("user");
			List bookings = flightticketdao.listFlightsByUser(currentUser.getUser_id());
			System.out.println(bookings.size());
			request.setAttribute("bookings", bookings);
			return "deleteBookings";
		}
		return "";

	}

	@PostMapping("manageBookings.htm")
	public String manageBookingPostHandler(HttpServletRequest request, HttpServletResponse response,
			FlightTicketDAO flightticketdao) throws FlightException, IOException {
		if (!interceptor.userIntercept(request, response)) {
			response.sendRedirect("/EzFlight/login.htm");
		}

		String action = request.getParameter("action");

		if (action.equals("deleteTicket")) {
			System.out.println(request.getParameter("ticketId"));
			long id = Long.parseLong(request.getParameter("ticketId"));
			if (flightticketdao.deleteTicketById(id)) {
				return "delete-successful";
			} else {
				return "delete-fail";
			}
		}
		return "";

	}

}
