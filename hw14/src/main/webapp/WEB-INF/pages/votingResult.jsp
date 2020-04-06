<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body bgcolor="${pickedBgCol}">
	<h1>Results of voting for favorite band.</h1>


	<table border="1">
		<tr>
			<td>Band</td><td>Number of votes</td>
		</tr>
		<c:forEach var="item" items="${options}">
			<tr>
				<td>${item.optionTitle}</td> <td>${item.votesCount}</td>
			</tr>
		</c:forEach>

	</table><br><br>
	
	<h2>Pie chart showing results.</h2>
	<img src="pieChartServlet?pollId=${poolId}">
	
	<h2>Results in XLS format.</h2>
	<p>You can download results <a href="gnerateExcel?pollId=${poolId}">here</a></p>
	
	
	<h2>Examples of wining bands</h2>
	
	<ul>
		<c:forEach var="topOptions" items ="${topOptions}">
			<li><a href="${topOptions.optionLink}" target="_blank">${topOptions.optionTitle}</a> </li>
		</c:forEach>
	</ul>
	
	<a href="glasanje?poolID=${poolId}">Back to voting page</a> <br>
	<a href="index.html">Back to home page.</a>
</body>

</html>