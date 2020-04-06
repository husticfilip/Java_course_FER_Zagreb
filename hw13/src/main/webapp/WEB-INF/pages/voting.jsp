<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body bgcolor="${pickedBgCol}">
	<h1>Voting for favorite band.</h1>
	
	<p>Which of following band is your favorite? Click on link to vote.<p>

	<ol>
		<c:forEach  var="entry" items = "${bandMap}">
			<li> <a href="voting-vote?id=${entry.key}"> ${entry.value.name} </a></li>
		</c:forEach>		
	</ol>
	
	<a href="voting-results">Results</a> <br><br>
		
	<a href="index.jsp">Back to home page.</a>
</body>

</html>