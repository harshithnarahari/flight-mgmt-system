package com.me.EzFlight.controllers;

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
import com.me.EzFlight.dao.LocationDAO;
import com.me.EzFlight.pojo.User;

@Controller
public class UserController {

	@Autowired
	MyInterceptor interceptor;

	@GetMapping("/userHome.htm")
	public String handleHomeGet(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			LocationDAO locationdao) throws FlightException {
		if (!interceptor.userIntercept(request, response)) {
			return "user-login";
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List locationList = locationdao.list();
		System.out.println(locationList.size());
		request.setAttribute("loginErr", "");
		model.addAttribute("locationList", locationList);
		return "userHome";

	}
}
