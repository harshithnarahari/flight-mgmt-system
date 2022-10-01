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


	<h1>Add Location</h1>
	<form:form modelAttribute ="location" action="manageLocation.htm?action=submit" method="post">
           Location Name: <form:input type="text" path="locationName" name="locationName"  id="locationName" onblur="checkLocation()" onClick="clearfield()"  /><br>
            <div id="locationNameError" style="color:red"></div>
            <form:errors path="locationName" /> <br>
            <input type="submit" value="Add Location" id="submitBtn"/>
                    
    </form:form>
</body>
</html>