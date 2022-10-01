<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
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
        <br>
        <br>
        <h1>Manage Your Bookings</h1>
	<a href="manageBookings.htm?action=view">View Bookings</a><br>
	<a href="manageBookings.htm?action=delete">Cancel Bookings</a>
</body>
</html>