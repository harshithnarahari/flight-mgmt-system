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
<script>
function updateFlight(){
	var reqId = document.getElementById("flightId").value;
	var table = document.getElementById("table");
	
	var rows = table.rows;
	
	for (var i = 1; i < rows.length; i++) {
	    var cols = rows[i].cells;
	    if(cols[0].innerText == reqId) {
	    	return true
	    }   	
	}
	document.getElementById("errMsg").innerHTML= "Invalid Flight Number";
	
	return false;
}

</script>

<c:choose>
            <c:when test="${!empty sessionScope.admin.firstName}">
                <jsp:include page="adDisp.jsp"/>
            </c:when>
            <c:otherwise>
                <jsp:include page="disp2.jsp"/>
            </c:otherwise>
        </c:choose>

	<h1>Update FLight</h1>
	<table border = "1" id="table">
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
            
        <form method ="POST" action="manageFlight.htm?action=update"  onSubmit="return updateFlight()">
    	Enter the Flight Number that you want to Update
    	<input type="number" name="flightId" id="flightId"/>
    	<input type="submit" value="update">
    	  <div id="errMsg"></div>
    	 </form>
           	
  
	
	
</body>
</html>