<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<script>
	
	function checkSeats(){
		var seats = document.getElementById('totalSeats').value;
		if(seats==0){
			document.getElementById("submitBtn").disabled = true;
			document.getElementById("errMsg").innerHTML = "Invalid Number of seats"
		}
		else if(seats<50 || seats> 150){
			document.getElementById("submitBtn").disabled = true;
			document.getElementById("errMsg").innerHTML = "Number of seats must be between 50 and 150"
		}
	}
	
	function checkPrice(){
		var price = document.getElementById('amount').value;
		if(price==0){
			document.getElementById("submitBtn").disabled = true;
			document.getElementById("errMsg").innerHTML = "Invalid Price"
		}
		if(price < 50){
			document.getElementById("submitBtn").disabled = true;
			document.getElementById("errMsg").innerHTML = "Minimum Price is 50$"
		}
		
	}
	
	function clearErr(){
		document.getElementById("errMsg").innerHTML= " ";
	}
	
	function checkTo(){
		var to = document.getElementById("toName").value;
		var from = document.getElementById("fromName").value;
		
		if(to == from){
			document.getElementById("errMsg").innerHTML = "from Location and To Location cant be same"
		}
	}

	function checkFName(){
		
		var xmlHttp;
		
		xmlHttp = GetXmlHttpObject();
		if (xmlHttp == null)
	    {
	        alert("Your browser does not support AJAX!");
	        return;
	    }
		
		var flightName = document.getElementById("flightName").value;
	  
	   
	    var reqQuery = "action=checkFName&flightName="+flightName;
	    //alert(reqQuery);
	    xmlHttp.onreadystatechange = function stateChanged()
	    {
	        if (xmlHttp.readyState == 4)
	        {
	        	 var json = JSON.parse(xmlHttp.responseText);
	             document.getElementById("errMsg").innerHTML="";
	             document.getElementById("errMsg").innerHTML = json.message;
	             if(json.message == "Flight Name already exists" || json.message=="Invalid Flight Name"){
	            	document.getElementById("submitBtn").disabled = true;
	             }
	             
	             else{
	            	 document.getElementById("submitBtn").disabled = false;
	             }
	        }
	    }
	    xmlHttp.open("POST", "validateFlight.htm", true);
	    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	    xmlHttp.send(reqQuery);
	    return false;
		
	}
	
function checkFlightNumber(){
		var xmlHttp;
		
		xmlHttp = GetXmlHttpObject();
		if (xmlHttp == null)
	    {
	        alert("Your browser does not support AJAX!");
	        return;
	    }
		
		var flightNumber = document.getElementById("flightNumber").value;
	  
	   
	    var reqQuery = "action=checkFNumber&flightNumber="+flightNumber;
	    //alert(reqQuery);
	    xmlHttp.onreadystatechange = function stateChanged()
	    {
	        if (xmlHttp.readyState == 4)
	        {
	        	 var json = JSON.parse(xmlHttp.responseText);
	             document.getElementById("errMsg").innerHTML="";
	             document.getElementById("errMsg").innerHTML = json.message;
	             if(json.message == "Flight Number already exists" || json.message=="Invalid Flight Number"){
	            	isValid = false;
	            	document.getElementById("submitBtn").disabled = true;
	             }
	             
	             else{
	            	 isValid = true;
	            	 document.getElementById("submitBtn").disabled = false;
	             }
	        }
	    }
	    xmlHttp.open("POST", "validateFlight.htm", true);
	    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	    xmlHttp.send(reqQuery);
	    return false;
		
	}
function GetXmlHttpObject()
{
var xmlHttp = null;
try
{
    // Firefox, Opera 8.0+, Safari
    xmlHttp = new XMLHttpRequest();
} catch (e)
{
    // Internet Explorer
    try
    {
        xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e)
    {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
}
return xmlHttp;
}
function checkDateAndTime(){
	var xmlHttp;
	
	xmlHttp = GetXmlHttpObject();
	if (xmlHttp == null)
    {
        alert("Your browser does not support AJAX!");
        return;
    }
	
	var date = document.getElementById("dateOfFlight").value;
	var time = document.getElementById("timeOfFlight").value;
	var flightNumber = document.getElementById("flightNumber").value;
	var to = document.getElementById("to").value;
	var from = document.getElementById("from").value;
	
	if(to == from){
		document.getElementById("errMsg").innerHTML = "from Location and To Location cant be same"
	} 
    var reqQuery = "action=checkDateTime&date="+date+"&time="+time+"&flightNumber="+flightNumber;
    //alert(reqQuery);
    xmlHttp.onreadystatechange = function stateChanged()
    {
        if (xmlHttp.readyState == 4)
        {
        	 var json = JSON.parse(xmlHttp.responseText);
             document.getElementById("errMsg").innerHTML="";
             document.getElementById("errMsg").innerHTML = json.message;
             if(json.message == "Flight with given date and time already exists"){
            
            	document.getElementById("submitBtn").disabled = true;
             }
             
             else{
           
            	 document.getElementById("submitBtn").disabled = false;
             }
        }
    }
    xmlHttp.open("POST", "manageFlight.htm", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send(reqQuery);
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
        <h1>Update Flight</h1>
        <br>
        <div>
        	<form method="POST" action="manageFlight.htm?action=updateFlight">
		Flight Name:
		<input type="text" name="flightName" value=${requestScope.flight.flightName} id="flightName" onblur="checkFName()" onClick="clearfield()"> <br>
		<input type="hidden" name="flightNumber" value=${requestScope.flight.flight_no}  />
		Date
		<input type="date" name="date" value=${requestScope.flight.date} id="date" onblur="checkDateAndTime()" onClick="clearfield()"> <br>
		Time
		<input type="time" name="time" value=${requestScope.flight.time} id="time" onblur="checkDateAndTime()" onClick="clearfield()"> <br>
		Total Seats: <input type="number" value=${requestScope.flight.totalSeats}  id="totalSeats" name="totalSeats" onblur="checkSeats()" /><br>
        Ticket Price: <input type="number" value=${requestScope.flight.amount} id="amount" name="amount" onblur="checkPrice()" /> <br>
        From: <select  onblur="checkTo()" id="fromName" name="fromName">
			 			<option>${requestScope.flight.from.locationName}</option>
			 			
						<c:forEach var="loc" items="${requestScope.locationList}" >
							<option ><c:out value="${loc.locationName}"/></option>
							
						</c:forEach>
			    </select>
        <br>
			To: <select  onblur="checkTo()" id="toName" name="toName" >
			 			<option>${requestScope.flight.to.locationName}</option>
			 			
						<c:forEach var="loc" items="${requestScope.locationList}" >
							<option ><c:out value="${loc.locationName}"/></option>
							
						</c:forEach>
			    </select>
			Airline: <select  onblur="checkTo()" id="airlineName" name="airlineName" >
			 			<option>${requestScope.flight.airline.airlineName}</option>
			 			
						<c:forEach var="airline" items="${requestScope.airlineList}" >
							<option ><c:out value="${airline.airlineName}"/></option>
							
						</c:forEach>		
						</select>
						<input type="submit" id="submitBtn" value="update">
						<div id="errMsg" style="color:red"></div> 
						
		
	</form>
        	
        </div>
	
</body>
</html>