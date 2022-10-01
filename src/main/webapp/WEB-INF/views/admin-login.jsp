<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Login</title>
</head>
<body>
	<h3 align="center">Please enter your credentials</h3>
	
<form action="login.htm" method="post">
<div><c:out value="${requestScope.loginErr}"/></div> 
<table align="center">

<tr>
    <td>Email:</td>
    <td><input type="text" name="email" size="30" required/></td>
</tr>

<tr>
    <td>Password:</td>
    <td><input type="password" name="password" size="30"  required/></td>
</tr>

<tr>
<td colspan="2"><input type="submit" value="Login" /></td>
</tr>

</table>
</form>
    
</body>
</html>