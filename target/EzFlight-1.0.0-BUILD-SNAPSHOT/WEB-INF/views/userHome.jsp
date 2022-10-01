<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EzFlight</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
 <script>
 

 function checkTo(){
		var to = document.getElementById("to").value;
		var from = document.getElementById("from").value;
		
		if(to == from){
			document.getElementById("errMsg").innerHTML = "from Location and To Location cant be same"
		}
		else{
			document.getElementById("errMsg").innerHTML =" ";
		}
	}       

        
       
</script>       
<style>
input:required:focus {
  border: 1px solid red;
  outline: none;
}
</style>         
                
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
 
 
<h1>Book your Trip</h1>
<form:form action="listflights.htm" method="post">
<div><c:out value="${requestScope.bookingErr}"/></div>
<table>

<tr>
	<td>From: </td>
	<td>
		<select onblur="checkTo()" id="to" name="from" onClick="check()">
			<option value="" selected disabled hidden>Choose here</option>
			<c:forEach var="loc" items="${locationList}">
			 	<option value="${loc.locationName}" /><c:out value="${loc.locationName }"></c:out>
			</c:forEach>
		</select>
	</td>
</tr>

<tr>
	<td>To: </td>
	<td>
		<select onblur="checkTo()" id="to" name="to" onClick="check()">
			<option value="" selected disabled hidden>Choose here</option>
			<c:forEach var="loc" items="${locationList}">
			 	<option value="${loc.locationName}" /><c:out value="${loc.locationName }"></c:out>
			</c:forEach>
		</select>
	</td>
</tr>



<tr>
    <td>Departure Date:</td>
    <td><input type="date"  id="txtFromDate" size="30" name="departureDate" required="required"/> <font color="red">
</tr>

<tr>
    <td>Return Date:</td>
    <td><input type="date"  id="txtReturnDate" size="30" name="returnDate" required="required"/> <font color="red">
</tr>



<tr>
    <td colspan="2"><input type="submit" value="Search flights" /></td>
</tr>
</table>

</form:form>
 
  <div>${requestScope.errMsg}</div>
    
    
</body>
</html>