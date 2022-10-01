package com.me.EzFlight.Interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.EzFlight.pojo.User;

public class MyInterceptor {

	public boolean adminIntercept(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("admin");
		if (admin == null) {
			return false;
		}
		if (admin.getRole().equalsIgnoreCase("admin")) {
			return true;
		}

		return false;

	}

	public boolean userIntercept(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return false;
		}
		if (user.getRole().equalsIgnoreCase("user")) {
			return true;
		}

		return false;
	}

}
