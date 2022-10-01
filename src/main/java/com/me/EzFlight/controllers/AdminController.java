package com.me.EzFlight.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.Interceptor.MyInterceptor;
import com.me.EzFlight.dao.AirlineDAO;
import com.me.EzFlight.dao.FlightDAO;
import com.me.EzFlight.dao.LocationDAO;
import com.me.EzFlight.pojo.Airline;
import com.me.EzFlight.pojo.Flight;
import com.me.EzFlight.pojo.Location;
import com.me.EzFlight.pojo.User;

@Controller
public class AdminController {

	@Autowired
	MyInterceptor interceptor;

	@GetMapping("admin/adminHome.htm")
	public String adminHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!interceptor.adminIntercept(request, response)) {
			return "admin-login";
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return "AdminHome";
	}

	@GetMapping("adminHome.htm")
	public String adminHomeHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("called");
		if (!interceptor.adminIntercept(request, response)) {
			return "admin-login";
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return "admin-login";
	}

	@GetMapping("admin/manageLocation.htm")
	public String manageLocation(ModelMap model, Location location, HttpServletRequest request,
			HttpServletResponse response, LocationDAO locationdao) throws FlightException, IOException {
		if (!interceptor.adminIntercept(request, response)) {
			return "admin-login";
		}
		String action = request.getParameter("action");
		if (action == null) {

			return "manage-location";
		} else if (action.equals("add")) {
			model.addAttribute(location);
			List locationList = locationdao.list();
			model.addAttribute("locationList", locationList);
			return "addLocation";
		} else if (action.equals("update")) {
			List locationList = locationdao.list();
			model.addAttribute("locationList", locationList);
			return "updateLocation";
		} else if (action.equals("delete")) {
			List locationList = locationdao.list();
			model.addAttribute("locationList", locationList);
			return "deleteLocation";
		} else if (action.equals("view")) {
			List locationList = locationdao.list();
			model.addAttribute("locationList", locationList);
			return "viewLocations";
		}

		return "manage-location";

	}

	@GetMapping("admin/manageAirline.htm")
	public String manageAirline(ModelMap model, Airline airline, HttpServletRequest request,
			HttpServletResponse response, AirlineDAO airlinedao) throws IOException, FlightException {

		if (!interceptor.adminIntercept(request, response)) {
			response.sendRedirect("/EzFlight/admin/login.htm");
		}
		String action = request.getParameter("action");
		if (action == null) {
			return "manage-airline";
		} else if (action.equals("add")) {
			airline = new Airline();
			model.addAttribute("airline", airline);
			return "addAirline";
		} else if (action.equals("update")) {
			List airlineList = airlinedao.list();
			model.addAttribute("airlineList", airlineList);
			return "updateAirline";
		} else if (action.equals("delete")) {
			List airlineList = airlinedao.list();
			model.addAttribute("airlineList", airlineList);
			return "deleteAirline";
		} else if (action.equals("view")) {
			List airlineList = airlinedao.list();
			model.addAttribute("airlineList", airlineList);
			return "viewAirlines";
		}

		return "manage-airline";
	}

	@GetMapping("admin/manageFlight.htm")
	public String manageFlights(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			AirlineDAO airlinedao, LocationDAO locationdao, FlightDAO flightdao) throws IOException, FlightException {

		if (!interceptor.adminIntercept(request, response)) {
			return "admin-login";
		}

		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("admin");
		if (admin == null) {
			response.sendRedirect("/EzFlight/admin/login.htm");
		}
		String action = request.getParameter("action");

		if (action == null) {

			return "manage-flights";
		} else if (action.equals("add")) {
			List airlineList = airlinedao.list();
			model.addAttribute("airlineList", airlineList);
			List locationList = locationdao.list();
			model.addAttribute("locationList", locationList);
			Flight flight = new Flight();
			model.addAttribute("flight", flight);

			return "addFlight";
		} else if (action.equals("update")) {
			List flightList = flightdao.list();
			model.addAttribute("flightList", flightList);
			return "updateFlight";
		} else if (action.equals("delete")) {
			List flightList = flightdao.list();
			model.addAttribute("flightList", flightList);
			return "deleteFlight";
		} else if (action.equals("view")) {
			List flightList = flightdao.list();

			model.addAttribute("flightList", flightList);
			return "viewFlights";
		}

		return "manage-location";

	}

}
