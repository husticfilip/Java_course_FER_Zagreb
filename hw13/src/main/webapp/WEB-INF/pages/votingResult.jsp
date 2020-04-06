<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body bgcolor="${pickedBgCol}">
	<h1>Results of voting for favorite band.</h1>


	<table border="1px solid black">
		<tr>
			<td>Band</td><td>Number of votes</td>
		</tr>
		<c:forEach var="bandEntry" items="${bandMap}">
			<tr>
				<td>${bandEntry.value.name}</td> <td>${bandEntry.value.numOfVotes}</td>
			</tr>
		</c:forEach>

	</table><br><br>
	
	<h2>Pie chart showing results.</h2>
	<img alt="Pie-chart" src="images/piecharts/resultChart.png" width="500" height="400"> <br><br>
	
	<h2>Results in XLS format.</h2>
	<p>You can download results <a href="files/excels/resultsExcel.xls" download>here</a></p>
	
	
	<h2>Examples of wining bands</h2>
	
	<ul>
		<c:forEach var="band" items ="${topBands}">
			<li><a href="${band.songLink}" target="_blank">${band.name}</a> </li>
		</c:forEach>
	</ul>
	
	<a href="vote">Back to voting page</a> <br>
	<a href="index.jsp">Back to home page.</a>
</body>

</html>