<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	
	<h1>Available polls</h1>
	<c:forEach items = "${polls}"  var="item">
		<a href="glasanje?poolID=${item.id}">${item.title}</a><br>
	</c:forEach>

</body>
</html>
