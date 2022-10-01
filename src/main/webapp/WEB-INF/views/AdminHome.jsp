<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Home</title>
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

	<h1>Admin Home</h1>
	<ul>
		
		
		<table border="1" align="center">
			<tr>
				<th>Manage Flights</th>
				<th>Manage Airlines</th>
				<th>Manage Location</th>
			</tr>
			<tr>
				<td><a href="manageFlight.htm?action=add">Add Flight</a></td>
				<td><a href="manageAirline.htm?action=add">Add Airline</a></td>
				<td><a href="manageLocation.htm?action=add">Add Location</a></td>
			</tr>
			<tr>
				<td><a href="manageFlight.htm?action=update">Update Flight</a></td>
				<td><a href="manageAirline.htm?action=update">Update Airline</a></td>
				<td><a href="manageLocation.htm?action=update">Update Location</a></td>
			</tr>
			<tr>
				<td><a href="manageFlight.htm?action=delete">Delete Flight</a></td>
				<td><a href="manageAirline.htm?action=delete">Delete Airline</a></td>
				<td><a href="manageLocation.htm?action=delete">Delete Location</a></td>
			</tr>
			<tr>
				<td><a href="manageFlight.htm?action=view">View Flights</a></td>
				<td><a href="manageAirline.htm?action=view">View Airlines</a></td>
				<td><a href="manageLocation.htm?action=view">View Locations</a></td>
			</tr>
		</table>
		
	</ul>

</body>
</html>