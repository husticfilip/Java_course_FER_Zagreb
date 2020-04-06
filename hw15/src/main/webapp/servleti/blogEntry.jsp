<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style>
table {
  	font-family: arial, sans-serif;
  	border-collapse: collapse;
  	width: 50%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}
</style>
</head>

<body>

		<c:choose>
			<c:when test="${! empty ActiveUserNick}">
				   <div align="right" >
						You are logged in as: <c:out value="${ActiveUserNick}"></c:out>     <br><a href="/webapp-blog/servleti/logout">Logout</a>
				   </div>
			</c:when>
			<c:otherwise>
					<br><br>
			</c:otherwise>	
		</c:choose>


<h2>This is blog entry of user ${blogEntry.blogUser.nick}</h2>
<table>
	<col width="20">
	<col width="300">
	<tr>
		<th>${blogEntry.title}</th>
		<th>${blogEntry.text}</th>
	</tr>
</table>
	<c:choose>
		<c:when test="${ActiveUserNick eq blogEntry.blogUser.nick}">
			<form action="/webapp-blog/servleti/edit/${blogEntry.blogUser.nick}/${blogEntry.id}" method="GET">
				<input type="submit" value="Edit">
			</form>
		</c:when>
	</c:choose>


	
	<br><br>
	<p><h2>Comments of this blog entry:</h2>
	
		<c:choose>
			<c:when test="${ empty entryComments }">
				There are no commnets for this entry.
			</c:when>
			<c:otherwise>			
				<table>
					<c:forEach items="${entryComments}" var="comment">
						<tr>
							<th>${comment.usersNick}</th>
							<th>${comment.message}</th>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
	 <p>
	
	<br><br>
	<c:choose>
			<c:when test="${! empty ActiveUserNick}">
				 <p>Add comment</p>
				 <form action="/webapp-blog/servleti/comment?entryId=${blogEntry.id}" method="POST">
				 	<textarea name="comment" cols="100" rows="5" maxlength="4096" required="required"></textarea> <br>
				 	<input type="submit" name="Add comment">
				 </form>
			</c:when>	
	</c:choose>
	
	
	<br>
	<a href="/webapp-blog/servleti/author/${blogEntry.blogUser.nick}">Go to user page</a><br>
	<a href="/webapp-blog/">Go to homepage</a>
	


</body>
</html>