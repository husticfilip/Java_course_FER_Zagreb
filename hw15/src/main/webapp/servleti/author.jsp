<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	  <c:choose>
				<c:when test="${! empty ActiveUserNick}">
				   <div align="right" >
						You are logged in as: <c:out value="${ActiveUserNick}"></c:out>     <br><a href="/webapp-blog/servleti/logout">Logout</a>
				   </div>
				</c:when>	
	</c:choose>
	
	
	<h2>Here you can find list of blog titles from user <c:out value="${blogUserNick}"></c:out> </h2> 
	
			<ol>
	<c:forEach items="${entires}" var="entry">
 				 <li><a href="/webapp-blog/servleti/author/${blogUserNick}/${entry.id}"> ${entry.title} </a></li>
	</c:forEach>
			</ol>
	
	<c:choose>
		<c:when test="${ActiveUserNick eq blogUserNick}">
			<a href="/webapp-blog/servleti/newEntry.jsp">Enter new blog entry</a>
			
		</c:when>
		
	</c:choose>


	<br><br>
	<a href="/webapp-blog/">Go to homepage</a>
</body>
</html>