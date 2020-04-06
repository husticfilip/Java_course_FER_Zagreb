<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Random"%>
<html>


<body bgcolor="${pickedBgCol}">
	
	<% List<String> colors= new ArrayList<>();
	   colors.add("#000000");	
	   colors.add("#b30000");		
	   colors.add("#003300");		
	   colors.add("#ffff00");		
	   colors.add("#999900");
	   
	   int index = (int)(Math.random()*(colors.size()-1));
	   String color = colors.get(index);
	  %>
	
	<font color = <% out.print(color);%>>YOU.</font>	
 	
 	
 	<br><br>
	<a href="/webapp1">Back to home page.</a>

</body>

</html>