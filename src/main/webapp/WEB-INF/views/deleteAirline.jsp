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
	function deleteAirline(){
		var reqId = document.getElementById("airlineId").value;
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
        <br>
        <div align="center"> 
        <h1>Delete Airline</h1>
	<table border = "1" id="table">
           	<tr>
           		<th>Airline Id</th>
           		<th>Airline Name</th>
           	</tr>
           	<c:forEach var="loc" items="${airlineList}">
                <tr>
                    <td>${loc.airline_id}</td>
                    <td>${loc.airlineName}</td>
                    
                </tr>
                
            </c:forEach>
            
           	
    </table>
    <br>
    
    <form method ="POST" action="manageAirline.htm?action=delete"  onSubmit="return deleteAirline()">
    	Enter the ID of the Airline that you want to delete
    	<input type="number" name="airlineId" id="airlineId"/>
    	<input type="submit" value="Delete">
    	
    </form> 
    
    <div id="errMsg"></div>
        </div>

</body>
</html>