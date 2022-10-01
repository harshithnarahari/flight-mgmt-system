<%@page import="org.springframework.web.context.request.RequestScope"%>
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

<script>

	var isValid = false;
	
		
	function checkAirline(){
		var flag = false;
		var xmlHttp;
		xmlHttp = GetXmlHttpObject();
		if (xmlHttp == null)
	    {
	        alert("Your browser does not support AJAX!");
	        return;
	    }
	    var airlineName = document.getElementById("airlineName").value;
	   
	   
	    var reqQuery = "action=check&airline="+airlineName;
	    //alert(reqQuery);
	    xmlHttp.onreadystatechange = function stateChanged()
	    {
	        if (xmlHttp.readyState == 4)
	        {
	        	 var json = JSON.parse(xmlHttp.responseText);
	             document.getElementById("airlineNameError").innerHTML="";
	             document.getElementById("airlineNameError").innerHTML = json.message;
	             if(json.message == "airline already exists" || json.message=="Invalid airline"){
	            	isValid = false;
	            	document.getElementById("submitBtn").disabled = true;
	             }
	             
	             else{
	            	 isValid = true;
		             document.getElementById("submitBtn").disabled = false;

	            	
	             }
	        }
	    }
	    xmlHttp.open("POST", "checkAirlineName.htm", true);
	    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	    xmlHttp.send(reqQuery);
	    return false;
		
	}

	// Get the XmlHttpObject
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
	function clearfield()
	{	
		document.getElementById("airlineNameError").innerHTML= " ";
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
        <br>
     <div align="center">
     <h1>Update airline <c:out value="${requestScope.airline.airlineName}" /> </h1>
     
	
	<form method="POST" action="manageAirline.htm?action=updateAirline">
		airline Name:
		<input type="text" name="airlineName" value=${requestScope.airline.airlineName} id="airlineName" onblur="checkAirline()" onClick="clearfield()">
		<input type="hidden" name="airlineId" value=${requestScope.airline.airline_id} />
		<input type="submit" id="submitBtn" value="update">
		
	</form>
			<div id="airlineNameError" style="color:red"></div>
     
     </div> 

	
	

</body>
</html>