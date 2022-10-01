package com.me.EzFlight.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.Interceptor.MyInterceptor;
import com.me.EzFlight.dao.AirlineDAO;
import com.me.EzFlight.pojo.Airline;

@Controller
public class AirlineController {

	@Autowired
	MyInterceptor interceptor;

	@PostMapping("admin/manageAirline.htm")
	public String airlineHandler(@Valid @ModelAttribute("airline") Airline airline, BindingResult result,
			SessionStatus status, HttpServletRequest request, HttpServletResponse response, AirlineDAO airlinedao)
			throws IOException, JSONException, FlightException, ServletException {

		if (!interceptor.adminIntercept(request, response)) {
			return "admin-login";
		}

		String airlineName = request.getParameter("airline");
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("submit")) {

			if (result.hasErrors()) {
				return "addAirline";
			}
			System.out.println("Add");
			airlinedao.addAirline(airline);
			return "add-success";

		} else if (action.equalsIgnoreCase("delete")) {
			long id = Long.parseLong(request.getParameter("airlineId"));
			airlinedao.deleteById(id);
			return "delete-successful";
		} else if (action.equalsIgnoreCase("update")) {
			long id = Long.parseLong(request.getParameter("airlineId"));
			Airline airLine = airlinedao.searchAirlineById(id);
			request.setAttribute("airline", airLine);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/updateAirlineForm.jsp");
			rd.forward(request, response);
		} else if (action.equalsIgnoreCase("updateAirline")) {
			long airlineId = Long.parseLong(request.getParameter("airlineId"));
			airlineName = request.getParameter("airlineName");
			System.out.println(airlineName);
			int output = airlinedao.updateAirline(airlineId, airlineName);
			if (output == 1) {
				return "update-success";
			} else if (output == 0) {
				return "update-fail";
			}

		}
		return "";
	}

	@PostMapping("admin/checkAirlineName.htm")
	public void checkAirlineName(HttpServletRequest request, HttpServletResponse response, AirlineDAO airlinedao)
			throws IOException, JSONException {

		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String airlineName = request.getParameter("airline");
		String regex = "^[a-zA-Z\\s]*$";

		if (airlineName.matches(regex)) {
			if (airlinedao.airlineExists(airlineName)) {

				json.put("message", "Airline already exists");
				out.println(json);

			} else {

				json.put("message", "Airline Name available");
				out.println(json);

			}
		} else {
			json.put("message", "Invalid  Name");
			out.println(json);
		}

	}

}
