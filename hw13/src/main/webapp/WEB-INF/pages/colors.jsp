<%@page import="java.nio.file.Paths"%>
<%@ page import="java.nio.file.*"%>
<%@ page session="true"%>
<html>
<head>
<style>
body {
	background-color: <%String color = (String) session.getAttribute("pickedBgCol");
			color = color == null ? "#ffffff" : "#" + color;
			out.print(color);%>;
}
</style>
</head>

<body>
	<a href="setColor?color=ffffff">WHITE</a>
	<br>
	<br>
	<a href="setColor?color=ffb3b3">RED</a>
	<br>
	<br>
	<a href="setColor?color=33cc33">GREEN</a>
	<br>
	<br>
	<a href="setColor?color=4da6ff">CYAN</a>
	<br>
	<br>
	<br>

	<a href="index.jsp">Back to home page.</a>
</body>
</html>