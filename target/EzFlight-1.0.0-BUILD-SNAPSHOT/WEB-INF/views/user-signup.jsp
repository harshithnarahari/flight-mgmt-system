<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script>
var emailFlag = false;
var fNameflag = false;
var lNameFlag = false;
var addressFlag = false;
var phoneFlag = false;
var passwordFlag = false;

function clearEmail()
{	
	document.getElementById("emailError").innerHTML= " ";
}


// Validation to Check Email
function checkEmail(){

	var xmlHttp;
	xmlHttp = GetXmlHttpObject();
	if (xmlHttp == null)
    {
        alert("Your browser does not support AJAX!");
        return;
    }
    var email = document.getElementById("email").value;
   
    var reqQuery = "email="+email;
    //alert(reqQuery);
    xmlHttp.onreadystatechange = function stateChanged()
    {
        if (xmlHttp.readyState == 4)
        {
        	 var json = JSON.parse(xmlHttp.responseText);
             document.getElementById("emailError").innerHTML="";
             document.getElementById("emailError").innerHTML = json.message;
             if(json.message == "Email already exists" || json.message=="Invalid Email"){
            	
            	 document.getElementById("submitBtn").disabled = true;
             }
             
             else{
            	 document.getElementById("submitBtn").disabled = false;	 
            
             }
        }
    }
    xmlHttp.open("POST", "checkEmail.htm", true);
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



</script>
	<h1>Signup Page</h1>
	<form:form modelAttribute ="user" action="register.htm?action=signup" onSubmit="return registerUser()" method="POST">
            First Name: <form:input type="text" path="firstName"  id="firstName"/>
           <br>
            Last Name: <form:input type="text" path="lastName"  id="lastName" /> 
            <br>
            Email: <form:input type="email" id="email" onblur="return checkEmail()"  onclick="return clearEmail()" path="email" />
       		<div id="emailError" style="color:red"></div>
            Phone: <form:input type="text" path="phone" id="phone"/> <br>
            Address: <form:input type="text" path="address" id="address"/><br>
            Password: <form:input type="password" path="password"  id="password" /><br>
           
            <input type="submit" value="REGISTER" id="submitBtn"/>
            
            <div>
            	 <form:errors path="firstName" /> <br>
            	 <form:errors path="lastName" /> <br>
            	 <form:errors path="phone" /><br>
            	 <form:errors path="address" /><br>
            	 <form:errors path="password" /><br>
            	 
            	 
            </div>
    </form:form>
    
    
    
</body>
</html>