<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<body bgcolor="${pickedBgCol}">

	<table border="1px solid black">
		<tr>
			<th>x</th>
			<th>sin(x)</th>
			<th>cos(x)</th>
		</tr>
		
		<c:forEach var="var" items="${sinCosList}">
		<tr>
			<td>${var.x}</td>
			<td>${var.sinX}</td>
			<td>${var.cosX}</td>
		</tr>
	</c:forEach>
	</table>
	
	<br>	
	<a href="index.jsp">Back to home page.</a>

</body>
</html>
