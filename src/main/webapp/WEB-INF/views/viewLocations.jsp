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

	<br>
	<br>
	<table border = "1" align="center">
           	<tr>
           		<th>Location Id</th>
           		<th>Location Name</th>
           	</tr>
           	<c:forEach var="loc" items="${locationList}">
                <tr>
                    <td>${loc.location_id}</td>
                    <td>${loc.locationName}</td>
                </tr>
            </c:forEach>
           	
    </table> 

</body>
</html>