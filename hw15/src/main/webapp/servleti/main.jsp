<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<style type="text/css">
 input {
	border-color: black;
	margin: 5px;
	}
	


</style>
</head>
<body>
	
		<c:choose>
				<c:when test="${! empty ActiveUserNick}">
				   <div align="right" >
						You are logged in as: <c:out value="${ActiveUserNick}"></c:out>     <br><a href="servleti/logout">Logout</a>
				   </div>
				</c:when>
				<c:otherwise>
					<br><br>
				</c:otherwise>	
	</c:choose>


	<h1>Welcome to my blog web application 
	
	</h1>
	
		
	<c:choose>
		<c:when test="${empty ActiveUserNick}">
			Login to my blog:
			
			<c:choose>
				<c:when test="${!empty LoginError}">
					<br><c:out value="${LoginError}"></c:out>
				</c:when>
			</c:choose>
			
			<form action="servleti/login" method="POST">
				Nickname:<input type="text" name="nick" value="Nickname" required="required"/> <br>
				Password: <input type="password" name="password" value="Password" required="required"> <br>
				<input type="submit" value="Login">
			</form>
			
			If you have not yet, register here:<br>
			<a href="servleti/register.jsp">Register</a>
		</c:when>
	</c:choose>
	
	

	

	<br><br>
	<p>
	List of registered authors:<br>
	<c:forEach items="${users}" var="user" >
	  <ul>
	 	<li> <a href="servleti/author/${user.nick}">${user.nick}</a></li>
	  </ul>
	</c:forEach>
	
	<p>
</body>
</html>
