<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<c:choose>
            <c:when test="${!empty sessionScope.user.firstName}">
                <jsp:include page="disp.jsp"/>
            </c:when>
            <c:otherwise>
                <jsp:include page="disp2.jsp"/>
            </c:otherwise>
        </c:choose>
	
	<form action="manageBookings.htm?action=deleteTicket" method="post">
	<table border = "1">
           	<tr>
           		<th>Booking ID <Id>
           		<th>Departure Flight Number</th>
           		<th>Departure Flight Name</th>
           		<th>Departure Date</th>
           		<th>Departure Time</th>
           		<th>Departure From</th>
           		<th>Arrival To</th>
           		<th>Departure Airline</th>
        		<th>Return Flight Number</th>
           		<th>Return Flight Name</th>
           		<th>Return Date</th>
           		<th>Return Time</th>
           		<th>Return From</th>
           		<th>Arrival To</th>
           		<th>Return Airline</th>   		
        
           		
           	</tr>
           	<c:forEach var="booking" items="${requestScope.bookings}">
                <tr>
                	<td><input type="radio" id="ticketId" name="ticketId" value="${booking.ticket_id}" onClick="btnHandler()"/></td>
                	<td>${booking.ticket_id}</td>
                    <td>${booking.depFlight.flight_no}</td>
                    <td>${booking.depFlight.flightName}</td>
                    <td>${booking.depFlight.date}</td>
                    <td>${booking.depFlight.time}</td>
                    <td>${booking.depFlight.from.locationName}</td>
                    <td>${booking.depFlight.to.locationName}</td>
                    <td>${booking.depFlight.airline.airlineName}</td>
                    <td>${booking.retFlight.flight_no}</td>
                    <td>${booking.retFlight.flightName}</td>
                    <td>${booking.retFlight.date}</td>
                    <td>${booking.retFlight.time}</td>
                    <td>${booking.retFlight.from.locationName}</td>
                    <td>${booking.retFlight.to.locationName}</td>
                    <td>${booking.retFlight.airline.airlineName}</td>
                </tr>
            </c:forEach>
           	
    </table> 
    
    	<input type="submit" value="cancel">
	
		
	</form>

		

</body>
</html>