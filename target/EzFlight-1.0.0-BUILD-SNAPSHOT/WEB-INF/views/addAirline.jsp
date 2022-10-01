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
<body>
	<script>
	
	
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
	             document.getElementById("airlineError").innerHTML="";
	             document.getElementById("airlineError").innerHTML = json.message;
	             if(json.message == "Airline already exists" || json.message=="Invalid Airline"){
	            	
	            	document.getElementById("submitBtn").disabled = true;
	             }
	             
	             else{
	            	
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
		document.getElementById("airlineError").innerHTML= " ";
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

	<h1>Add Airline</h1>
	<form:form modelAttribute ="airline" action="manageAirline.htm?action=submit" method="post" >
           Airline Name: <form:input type="text" path="airlineName"  id="airlineName" onblur="checkAirline()" onClick="clearfield()"  /><br>
            
            <input type="submit" id="submitBtn" value="Add Airline"/>  
            <form:errors path="airlineName" /> <br>      
            <div id="airlineError" style="color:red"></div>
            
    </form:form>
</body>
</html>