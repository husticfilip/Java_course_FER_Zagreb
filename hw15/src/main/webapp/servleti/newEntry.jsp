<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<style type="text/css">
 input {
	margin: 5px;
	}
textarea {
	margin: 5px;
}	


</style>
</head>

<body>
	<h2>Here you will find form for filling up new blog entry</h2>

	<c:choose>

		<c:when test="${! empty ActiveUserNick }">
			<form action="/webapp-blog/servleti/author/${ActiveUserNick}/new" method="POST">
				<input type="text" name="title" value="Entry title" required="required" maxlength="200"><br> 
				<textarea name="text" cols="100" rows="5" maxlength="4096"></textarea> <br>
				<input type="submit" value="Submit">
			</form>

		</c:when>
		<c:otherwise>
			Sorry but you are not logged in so you can not enter blog entry.
		</c:otherwise>

	</c:choose>


	<br><br>
	<a href="/webapp-blog/">Go to homepage</a>

</body>
</html>