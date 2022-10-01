<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
	<h1>Flights from ${sessionScope.destination} to ${sessionScope.source}</h1>
	<form action="bookFlight.htm?action=returnFlight" method="post">
	<table border="1">
		<tr>
			<th></th>
			<th>Airline</th>
			<th>Flight Name</th>
			<th>Time</th>
			<th>Price</th>
			<th>Seats Available</th>
			
		</tr>
		<c:forEach var="flight" items="${sessionScope.destToSrc}">
			<tr>
				
				<td>
					<input type="radio" name="flightNumber" value="${flight.flight_no}" onClick = "btnHandler()" />
				</td>
				<td>${flight.airline.airlineName}</td>
				<td>${flight.flightName}</td>
				<td>${flight.time}</td>
				<td>${flight.amount}</td>
				<td>${flight.availableSeats}</td>
				
					
			</tr>
    		
		</c:forEach>
		
	</table>
	
	<br>
		
	
	<input type="submit" id="submitBtn" value="View Summary"  >
	
	 <div>${requestScope.errMsgDest}</div>
	
	</form>
	
	<script>
	 	document.getElementById("submitBtn").disabled = true;
		
	 	function btnHandler(){
	 		
	 		document.getElementById("submitBtn").disabled = false;
	 	}
	
	</script>
	
	
	
	
</body>
</html>