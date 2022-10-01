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
            <c:when test="${!empty sessionScope.admin.firstName}">
                <jsp:include page="adDisp.jsp"/>
            </c:when>
            <c:otherwise>
                <jsp:include page="disp2.jsp"/>
            </c:otherwise>
        </c:choose>
	
	<table border = "1" align ="center">
           	<tr>
           		<th>Flight Number</th>
           		<th>Flight Name</th>
           		<th>Date</th>
           		<th>Time</th>
           		<th>From</th>
           		<th>To</th>
           		<th>Airline</th>
           		
           	</tr>
           	<c:forEach var="flight" items="${flightList}">
                <tr>
                    <td>${flight.flight_no}</td>
                    <td>${flight.flightName}</td>
                    <td>${flight.date}</td>
                    <td>${flight.time}</td>
                    <td>${flight.from.locationName}</td>
                    <td>${flight.to.locationName}</td>
                    <td>${flight.airline.airlineName}</td>
                </tr>
            </c:forEach>
           	
    </table> 
	

</body>
</html>