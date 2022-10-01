package com.me.EzFlight.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.dao.LocationDAO;
import com.me.EzFlight.dao.UserDAO;
import com.me.EzFlight.pojo.User;

@Controller
public class UserFormHandler {

	@GetMapping("login.htm")
	public String loginPageHandler() {
		return "user-login";
	}

	@GetMapping("register.htm")
	public String signupHandler(ModelMap model, User user) {
		model.addAttribute(user);
		return "user-signup";
	}

	@GetMapping("admin/login.htm")
	public String adminLoginHandler() {
		return "admin-login";
	}

	@PostMapping("register.htm")
	public String signupSubmitHandler(ModelMap model, @Valid @ModelAttribute("user") User user, BindingResult result,
			SessionStatus status, HttpServletRequest request, HttpServletResponse response, UserDAO userdao,
			LocationDAO locationdao) throws IOException, JSONException, FlightException {

		if (result.hasErrors()) {

			return "user-signup";
		}

		user.setRole("user");
		userdao.addUser(user);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		List locationList = locationdao.list();
		request.setAttribute("loginErr", "");
		model.addAttribute("locationList", locationList);
		session.setAttribute("user", user);

		return "userHome";

	}

	@PostMapping("checkEmail.htm")
	public void checkEmail(HttpServletRequest request, HttpServletResponse response, UserDAO userdao)
			throws IOException, JSONException {
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		boolean checkFlag = false;
		JSONObject json = new JSONObject();
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (email.matches(regex)) {

			// Check in DB for existing email
			if (userdao.userExists(email)) {

				json.put("message", "Email already exists");
				out.println(json);

			} else {
				json.put("message", "Email available");
				out.println(json);
			}
		} else {
			json.put("message", "Invalid Email");
			out.println(json);

		}

	}

	@PostMapping("searchFlights.htm")
	public String loginUser(ModelMap model, HttpServletRequest request, HttpServletResponse response, UserDAO userdao,
			LocationDAO locationdao) throws IOException, FlightException {
		String email = request.getParameter("email");
		HttpSession session = request.getSession();
		String password = request.getParameter("password");
		String action = request.getParameter("action");

		User user = userdao.getUser(email, password);
		if (user == null) {
			System.out.println("User not found");

			request.setAttribute("loginErr", "Invalid Credentials");
			return "user-login";
		} else {
			List locationList = locationdao.list();
			System.out.println(locationList.size());
			request.setAttribute("loginErr", "");
			model.addAttribute("locationList", locationList);
			session.setAttribute("user", user);
			return "userHome";
		}

	}

	@PostMapping("admin/login.htm")
	public String adminLoginHandler(HttpServletRequest request, HttpServletResponse response, UserDAO userdao) {
		String email = request.getParameter("email");
		HttpSession session = request.getSession();
		String password = request.getParameter("password");
		User user = userdao.getUser(email, password);
		if (user == null) {
			System.out.println("User not found");
			request.setAttribute("loginErr", "Invalid Credentials");
			return "admin-login";
		} else {
			request.setAttribute("loginErr", "");
			session.setAttribute("admin", user);
			return "AdminHome";
		}

	}

	@GetMapping("logout.htm")
	public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "home";
	}

	@GetMapping("admin/logout.htm")
	public void logoutAdminUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("/EzFlight/");
	}

}
