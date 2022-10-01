<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script>
	var isValid = false;
	function deleteLocation(){
		var reqId = document.getElementById("locId").value;
		var table = document.getElementById("table");
		
		var rows = table.rows;
		
		for (var i = 1; i < rows.length; i++) {
		    var cols = rows[i].cells;
		    if(cols[0].innerText == reqId) {
		    	return true
		    }   	
		}
		document.getElementById("errMsg").innerHTML= "Invalid Id";
		
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
<h1>Delete Location</h1>
	<table border = "1" id="table">
           	<tr>
           		<th>Location Id</th>
           		<th>Location Name</th>
           	</tr>
           	<c:forEach var="loc" items="${locationList}">
                <tr>
                    <td>${loc.location_id}</td>
                    <td>${loc.locationName}</td>
                    
                </tr>
                
            </c:forEach>
            
           	
    </table>
    
    <form method ="POST" action="manageLocation.htm?action=delete"  onSubmit="return deleteLocation()">
    	Enter the ID of the location that you want to delete
    	<input type="number" name="locId" id="locId"/>
    	<input type="submit" value="delete">
    	
    </form> 
    
    <div id="errMsg"></div>


</body>
</html>