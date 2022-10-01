<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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
	<h1>Itenary Summary</h1>
	
	<h1>${sessionScope.source} to ${sessionScope.destination }</h1>
	
	<br>
	<table>
	<tr>
		<th>Flight Number</th>
		<th>Flight Name</th>
		<th>Departing Date</th>
		<th>Time</th>
		<th>Amount</th>
	</tr>
	<tr>
		<td>${sessionScope.flightTicket.depFlight.flight_no }</td>
		<td>${sessionScope.flightTicket.depFlight.flightName }</td>
		<td>${sessionScope.flightTicket.depFlight.date }</td>
		<td>${sessionScope.flightTicket.depFlight.time }</td>
		<td>${sessionScope.flightTicket.depFlight.amount }</td>
	</tr>
		
	</table>
	
	<h1>${sessionScope.destination} to ${sessionScope.source }</h1>
	
	<table>
	<tr>
		<th>Flight Number</th>
		<th>Flight Name</th>
		<th>Departing Date</th>
		<th>Time</th>
		<th>Amount</th>
	</tr>
	<tr>
		<td>${sessionScope.flightTicket.retFlight.flight_no }</td>
		<td>${sessionScope.flightTicket.retFlight.flightName }</td>
		<td>${sessionScope.flightTicket.retFlight.date }</td>
		<td>${sessionScope.flightTicket.retFlight.time }</td>
		<td>${sessionScope.flightTicket.retFlight.amount }</td>
	</tr>
		
	</table>
	
	<h2>Total Price : ${sessionScope.flightTicket.totalPrice} </h2>
	
	<form action="bookFlight.htm?action=confirm" method="POST">
		<input type="submit" value="confirm booking">
	</form>
	
	

</body>
</html>