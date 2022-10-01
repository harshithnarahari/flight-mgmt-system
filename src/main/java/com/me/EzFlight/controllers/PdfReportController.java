package com.me.EzFlight.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import com.me.EzFlight.Exception.FlightException;
import com.me.EzFlight.Interceptor.MyInterceptor;
import com.me.EzFlight.PDF.MyPdfView;
import com.me.EzFlight.dao.FlightTicketDAO;
import com.me.EzFlight.pojo.FlightTicket;
import com.me.EzFlight.pojo.User;

@Controller
public class PdfReportController {

	@Autowired
	MyInterceptor interceptor;

	@GetMapping("/report")
	public View handleRequest(@RequestParam long id, HttpServletRequest request, HttpServletResponse response,
			FlightTicketDAO ft) throws FlightException, IOException {
		if (!interceptor.userIntercept(request, response)) {
			response.sendRedirect("/EzFlight/login.htm");
		}
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		List<FlightTicket> userTickets = currentUser.getUserTickets();
		boolean flag = false;
		if (ft.checkIfIdExists(id)) {
			for (FlightTicket ticket : userTickets) {
				if (ticket.getTicket_id() == id) {

					System.out.println("Found User Ticket");
					flag = true;
				}
				System.out.println(ticket.getTicket_id());
			}
			System.out.println("Loooped Thourgh tickets");

		} else {
			System.out.println("User Ticket Not Found");
		}

		if (flag) {
			FlightTicket ticket = ft.searchTicketById(id);

			View view = new MyPdfView(ticket);
			return view;
		} else {
			response.sendRedirect("/EzFlight/pdfErr.htm");
		}
		return null;

	}

	@GetMapping("/pdfErr.htm")
	public String pdfErrHandler() {
		return "pdfError";
	}

}
