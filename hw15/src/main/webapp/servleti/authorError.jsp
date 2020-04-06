<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>


	<h2>There were mistakes in getting blog entries.</h2>
	<br>

	<p>
		<c:out value="${ authorError}"></c:out>

	</p>

	<br><br>
	<a href="/webapp-blog/">Go to homepage</a>

</body>
</html>