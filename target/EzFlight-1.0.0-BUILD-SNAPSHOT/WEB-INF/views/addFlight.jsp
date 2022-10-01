<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body onload="test()">

	<script>
	
		
		
		function test(){
			document.getElementById('submitBtn').disabled = true;
		}
		
		
	
		function clearErr(){
			document.getElementById("errMsg").innerHTML= " ";
		}
		
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
	
		function checkFlightName(){
			
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
	    xmlHttp.open("POST", "validateFlight.htm", true);
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
	<h1>Add Flight</h1>
	
	<form:form modelAttribute ="flight" method="POST" action="manageFlight.htm?action=submit">
           Flight Number: <form:input type="number" path="flight_no"  id="flightNumber" onblur="checkFlightNumber()" onClick="clearErr()"/><br>
            Flight Name:<form:input type="text" path="flightName"  id="flightName"  onblur="checkFlightName()" onClick="clearErr()"/><br>
            Total Seats: <form:input type="number" path="totalSeats"  id="totalSeats" onClick="check()"  /><br>
            Ticket Price: <form:input type="number" path="amount" id="amount" onblur="checkPrice()" onClick="check()"/>
            From: <form:select path="from" id="from" onClick="check()">
             			<option value="" selected disabled hidden>Choose here</option>
						<c:forEach var="loc" items="${locationList}" >
							<form:option value="${loc.locationName}" />
						</c:forEach>
					</form:select>
			To: <form:select path="to" onblur="checkTo()" id="to" onClick="check()">
			 			<option value="" selected disabled hidden>Choose here</option>
						<c:forEach var="loc" items="${locationList}">
							<form:option value="${loc.locationName}" />
						</c:forEach>
					</form:select>
			Airline: <form:select path="airline" onClick="check()">
						<option value="" selected disabled hidden>Choose here</option>
						<c:forEach var="airline" items="${airlineList}">
							<form:option value="${airline.airlineName}" />
						</c:forEach>
					</form:select>
					
			Date:
			<input type="date" name="dateOfFlight" id="dateOfFlight"
        placeholder="dd-mm-yyyy" value=""
        min="1997-01-01" max="2030-12-31" onClick="clearErr()">
        Time:  <input type="time" name="timeOfFlight" value="13:00" id="timeOfFlight" onClick="clearErr()">
            <input type="submit" id="submitBtn" value="Add Airline"/>
            <div id="errMsg" style="color:red"></div>  
            <form:errors path="flightName" /> <br/>
            <form:errors path="flight_no" /> <br/>      
            <form:errors path="totalSeats" /> <br/>
            <form:errors path="amount" /> <br/>
             <form:errors path="to" /> <br/>
            <form:errors path="from" /> <br/>
             <form:errors path="date" /> <br/>
              <form:errors path="time" />
              <div>${requestScope.errMsg}</div>
            
           
    </form:form>
</body>
</html>