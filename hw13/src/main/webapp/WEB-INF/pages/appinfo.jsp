<%@page import="java.text.SimpleDateFormat"%>

<%
	long diff = System.currentTimeMillis() - (long) getServletContext().getAttribute("startUpTime");

	double helper = (double) diff / (1000 * 60 * 60 * 24);
	double days = Math.floor(helper);
	helper = (helper - days) * 24;
	double hours = Math.floor(helper);
	helper = (helper - hours) * 60;
	double min = Math.floor(helper);
	helper = (helper - min) * 60;
	double sec = Math.floor(helper);
	double milis = (helper - sec) * 1000;
%>

<html>
<body bgcolor="${pickedBgCol}">

	<h1>
		Web app is running for: <br>
		<%
			out.print((long) days + "days " + (long) hours + "hours " + (long) min + "min " + (long) sec + "sec "
					+ (long) milis + "milis ");
		%>
	</h1>

	<a href="index.jsp">Back to home page.</a>

</body>

</html>