<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <h1>User Signup Success Full</h1>
  <h1>User Registration Details</h1>
        First Name: ${requestScope.user.firstName} <br>
        Last Name: ${requestScope.user.lastName} <br>
        Email: ${requestScope.user.email} <br>
</body>
</html>