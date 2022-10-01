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
import com.me.EzFlight.dao.LocationDAO;
import com.me.EzFlight.pojo.Location;

@Controller
public class LocationController {

	@Autowired
	MyInterceptor interceptor;

	@PostMapping("admin/manageLocation.htm")
	public String checkLocation(@Valid @ModelAttribute("location") Location location, BindingResult result,
			SessionStatus status, HttpServletRequest request, HttpServletResponse response, LocationDAO locationdao)
			throws IOException, JSONException, FlightException, ServletException {

		if (!interceptor.adminIntercept(request, response)) {
			return "admin-login";
		}

		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("submit")) {
			if (result.hasErrors()) {
				return "addLocation";
			}
			locationdao.addLocation(location);

			return "add-success";
		} else if (action.equalsIgnoreCase("delete")) {
			long id = Long.parseLong(request.getParameter("locId"));
			locationdao.deleteById(id);
			return "delete-successful";
		} else if (action.equalsIgnoreCase("update")) {
			long id = Long.parseLong(request.getParameter("locId"));
			Location loc = locationdao.searchLocationById(id);
			request.setAttribute("location", loc);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/updateLocationForm.jsp");
			rd.forward(request, response);
		} else if (action.equalsIgnoreCase("updateLoc")) {
			long locationId = Long.parseLong(request.getParameter("locationId"));
			String locName = request.getParameter("locName");
			System.out.println(locName);
			int output = locationdao.updateLocation(locationId, locName);
			if (output == 1) {
				return "update-success";
			}
			return "update-fail";

		}

		return "";
	}

	@PostMapping("admin/checkLocation.htm")
	public void checkLocation(HttpServletRequest request, HttpServletResponse response, LocationDAO locationdao)
			throws IOException, JSONException {
		String locationName = request.getParameter("location");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String regex = "[A-Z][a-z]*";
		if (locationName.matches(regex) && locationName.length() > 0) {

			// Check in DB for existing email
			if (locationdao.locationExists(locationName)) {
				System.out.println("already Exits");
				json.put("message", "Location already exists");
				out.println(json);

			} else {
				System.out.println("location does not Exits");
				json.put("message", "Location available");
				out.println(json);
			}
		} else {
			json.put("message", "Invalid Location");
			out.println(json);

		}
	}

}
