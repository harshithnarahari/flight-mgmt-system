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
		
	function checkLocation(){
		var flag = false;
		var xmlHttp;
		xmlHttp = GetXmlHttpObject();
		if (xmlHttp == null)
	    {
	        alert("Your browser does not support AJAX!");
	        return;
	    }
	    var locationName = document.getElementById("locationName").value;
	   
	    var reqQuery = "action=check&location="+locationName;
	    //alert(reqQuery);
	    xmlHttp.onreadystatechange = function stateChanged()
	    {
	        if (xmlHttp.readyState == 4)
	        {
	        	 var json = JSON.parse(xmlHttp.responseText);
	             document.getElementById("locationNameError").innerHTML="";
	             document.getElementById("locationNameError").innerHTML = json.message;
	             if(json.message == "Location already exists" || json.message=="Invalid Location"){
	            	 document.getElementById("submitBtn").disabled = true;;
	             }
	             
	             else{
	            	 document.getElementById("submitBtn").disabled = false;;
	             }
	        }
	    }
	    xmlHttp.open("POST", "checkLocation.htm", true);
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
		document.getElementById("locationNameError").innerHTML= " ";
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

	<h1>Update Location <c:out value="${requestScope.location.locationName}" /> </h1>
	
	<form method="POST" action="manageLocation.htm?action=updateLoc">
		Location Name:
		<input type="text" name="locName" value="${requestScope.location.locationName}" id="locationName" onblur="checkLocation()" onClick="clearfield()">
		<input type="hidden" name="locationId" value=${requestScope.location.location_id} />
		<input type="submit" id="submitBtn" value="update">
		
	</form>
			<div id="locationNameError" style="color:red"></div>
	

</body>
</html>