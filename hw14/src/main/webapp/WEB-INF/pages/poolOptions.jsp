<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h1>Voting for favorite band.</h1>
	
	<p>Which of following band is your favorite? Click on link to vote.<p>

	<ol>
		<c:forEach  var="entry" items = "${options}">
			<li> <a href="registerVote?optionId=${entry.id}&pollId=${pollId}"> ${entry.optionTitle} </a></li>
		</c:forEach>		
	</ol>
	
	<a href="votingResults?pollId=${pollId}">Results</a> <br><br>
		
	<a href="index.html">Back to home page.</a>
</body>

</html>