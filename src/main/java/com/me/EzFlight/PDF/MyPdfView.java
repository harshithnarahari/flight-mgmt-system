package com.me.EzFlight.PDF;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.me.EzFlight.pojo.Flight;
import com.me.EzFlight.pojo.FlightTicket;
import com.me.EzFlight.pojo.User;

public class MyPdfView extends AbstractPdfView {

	private FlightTicket myTicket;

	public MyPdfView(FlightTicket ticket) {
		this.myTicket = ticket;
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		User passenger = myTicket.getUser();
		Flight departingFlight = myTicket.getDepFlight();
		Flight returnFlight = myTicket.getRetFlight();

		Paragraph title = new Paragraph("Below is Your Ticket Confirmation");
		Paragraph name = new Paragraph("Passenger name: " + passenger.getFirstName() + " " + passenger.getLastName());
		Paragraph seperator = new Paragraph(
				"----------------------------------------------------------------------------");
		Paragraph deptflight = new Paragraph("Flight Name: " + departingFlight.getFlightName() + " From "
				+ departingFlight.getFrom().getLocationName() + " To " + departingFlight.getTo().getLocationName());

		Paragraph retflight = new Paragraph("Flight Name: " + returnFlight.getFlightName() + " From "
				+ returnFlight.getFrom().getLocationName() + " To " + returnFlight.getTo().getLocationName());

		Paragraph deptDetails = new Paragraph(
				"Departure Date " + departingFlight.getDate() + " Departure Time :" + departingFlight.getTime());

		Paragraph retDetails = new Paragraph(
				"Return Date " + returnFlight.getDate() + " return Time :" + returnFlight.getTime());

		document.add(title);
		document.add(seperator);
		document.add(name);
		document.add(seperator);
		document.add(deptflight);
		document.add(deptDetails);
		document.add(seperator);
		document.add(retflight);
		document.add(retDetails);

	}

}
