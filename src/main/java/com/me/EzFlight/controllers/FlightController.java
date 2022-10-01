package com.me.EzFlight.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.Interceptor.MyInterceptor;
import com.me.EzFlight.dao.AirlineDAO;
import com.me.EzFlight.dao.FlightDAO;
import com.me.EzFlight.dao.LocationDAO;
import com.me.EzFlight.pojo.Airline;
import com.me.EzFlight.pojo.Flight;
import com.me.EzFlight.pojo.Location;

@Controller
public class FlightController {

	@Autowired
	MyInterceptor interceptor;

	@PostMapping("admin/manageFlight.htm")
	public String flightHandler(ModelMap model, @Valid @ModelAttribute("flight") Flight flight, BindingResult result,
			SessionStatus status, HttpServletRequest request, HttpServletResponse response, FlightDAO flightdao,
			LocationDAO locationdao, AirlineDAO airlinedao)
			throws FlightException, JSONException, IOException, ServletException {
		if (!interceptor.adminIntercept(request, response)) {
			return "admin-login";
		}
		String action = request.getParameter("action");

		if (action.equals("submit")) {

			if (result.hasErrors()) {
				List airlineList = airlinedao.list();
				model.addAttribute("airlineList", airlineList);
				List locationList = locationdao.list();
				model.addAttribute("locationList", locationList);
				Flight newFlight = new Flight();
				return "addFlight";
			}

			String date = request.getParameter("dateOfFlight");
			String time = request.getParameter("timeOfFlight");

			String fromLocationName = flight.getFrom().getLocationName();
			String toLocationName = flight.getTo().getLocationName();
			String airlineName = flight.getAirline().getAirlineName();

			if (date.isEmpty() || time.isEmpty() || fromLocationName.isEmpty() || toLocationName.isEmpty()
					|| airlineName.isEmpty()) {
				List airlineList = airlinedao.list();
				model.addAttribute("airlineList", airlineList);
				List locationList = locationdao.list();
				model.addAttribute("locationList", locationList);
				model.addAttribute("flight", flight);
				request.setAttribute("errMsg", "Please Enter all the fields");
				return "addFlight";
			}

			Location fromLocation = locationdao.searchLocationByName(fromLocationName);
			Location toLocation = locationdao.searchLocationByName(toLocationName);
			Airline airline = airlinedao.searchAirlineByName(airlineName);

			Flight newFlight = new Flight(fromLocation, toLocation, airline, flight.getFlight_no(),
					flight.getFlightName(), flight.getTotalSeats(), flight.getAmount(), date, time);

			flightdao.addFlight(newFlight);
			return "add-success";
		}

		else if (action.equalsIgnoreCase("delete")) {
			long flightNo = Long.parseLong(request.getParameter("flightId"));
			flightdao.deleteById(flightNo);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/delete-successful.jsp");
			rd.forward(request, response);
		} else if (action.equalsIgnoreCase("update")) {
			long id = Long.parseLong(request.getParameter("flightId"));
			flight = flightdao.searchFlightById(id);
			List airlineList = airlinedao.list();

			List locationList = locationdao.list();

			request.setAttribute("flight", flight);
			request.setAttribute("locationList", locationList);
			request.setAttribute("airlineList", airlineList);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/updateFlightForm.jsp");
			rd.forward(request, response);

		} else if (action.equalsIgnoreCase("updateFlight")) {
			long flightId = Long.parseLong(request.getParameter("flightNumber"));
			String flightName = request.getParameter("flightName");
			String date = request.getParameter("date");
			String time = request.getParameter("time");
			String fromName = request.getParameter("fromName");
			String toName = request.getParameter("toName");
			String airlineName = request.getParameter("airlineName");
			int totalSeats = Integer.parseInt(request.getParameter("totalSeats"));
			int amount = Integer.parseInt(request.getParameter("amount"));

			Location from = locationdao.searchLocationByName(fromName);
			Location to = locationdao.searchLocationByName(toName);
			Airline airline = airlinedao.searchAirlineByName(airlineName);
			Flight updatedFlight = flightdao.searchFlightById(flightId);

			updatedFlight.setAirline(airline);
			updatedFlight.setFrom(from);
			updatedFlight.setTo(to);
			updatedFlight.setFlightName(flightName);
			updatedFlight.setDate(date);
			updatedFlight.setTime(time);
			updatedFlight.setTotalSeats(totalSeats);
			updatedFlight.setAmount(amount);

			flightdao.updateFlight(updatedFlight);

			return "update-success";

		}

		return "";

	}

	@PostMapping("admin/validateFlight.htm")
	public void validateFlight(HttpServletRequest request, HttpServletResponse response, FlightDAO flightdao)
			throws IOException, JSONException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String action = request.getParameter("action");

		if (action.equals("checkFName")) {
			String flightName = request.getParameter("flightName");

			String regex = "^[a-zA-Z\\s]*$";
			// Check in DB for existing email
			if (flightName.matches(regex) && !flightName.isEmpty() && !flightName.isBlank()) {
				if (flightdao.flightNameExists(flightName)) {

					json.put("message", "Flight Name already exists");
					out.println(json);

				} else {
					json.put("message", "Flight Name available");
					out.println(json);

				}
			} else {
				json.put("message", "Invalid Flight Name");
				out.println(json);
			}

		} else if (action.equals("checkFNumber")) {
			String flightNumber = request.getParameter("flightNumber");

			// Check in DB for existing email
			if (flightdao.flightNumberExists(flightNumber)) {

				json.put("message", "Flight Number already exists");
				out.println(json);

			} else {

				json.put("message", "Flight Number available");
				out.println(json);
			}

		} else if (action.equals("checkDateTime")) {
			String date = request.getParameter("date");
			String time = request.getParameter("time");
			String flightNumber = request.getParameter("flightNumber");
			if (flightdao.flightWithDateAndTimeExists(date, time, flightNumber)) {
				json.put("message", "Flight with given date and time already exists");
				out.println(json);
			} else {
				json.put("message", "No Collision with other Date and Time");
				out.println(json);
			}

		}
	}

}
