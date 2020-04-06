<%@ page session="true"%>
<html>

<body bgcolor="${pickedBgCol}">
	<h1>Hello, available links:</h1>


	<a href="colors">Background color chooser.</a> <br>	<br>
	<a href="trigonometric?a=0&b=90">Trigonometric table.</a> <br><br>

	<form action="trigonometric" method="GET">
	
		Početni kut:<br> <input type="number" name="a" min="0" max="360" step="1" value="0"> <br>
	    Završni kut:<br> <input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	
	</form>
	<br><br>


	<a href="stories/funny.jsp">Funny story.</a><br><br>
	<a href="report">Os usage report.</a> <br><br>

	<form action="powers" method="GET"> 
	
		Start Number:<br> <input type="number" name="a" value="0"> <br>
		End Number:  <br> <input type="number" name="b" value="10"> <br>
		Powers:  <br> <input type="number" name="n" value="1"> <br>
		<input type="submit"  value="Generate Excel">
	
	</form>
	<br><br>
	
	<a href="appinfo">App info</a> <br><br>
	
	<a href="vote">Vote for your favorite band!</a>
	
	
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>