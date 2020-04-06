<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<h2>There were errors in your register form. <br><br>
	</h2>
	
 <p>
 Errors are: 
  <ol>
	<c:forEach var = "error" items = "${invalidReasons}">
		<li>${error} </li>
	</c:forEach>
  </ol>
 <p>
	
	<a href="register.jsp">Go to register page</a> <br>
	<a href="/webapp-blog/">Go to homepage</a>
</body>
</html>