<html>
<head>
<style type="text/css">
 input {
	border-color: black;
	margin-left: 5px;
	}


</style>
</head>

<body>
	<h1>Welcome to register page</h1>
	
	<form action="register" method="POST" >
		First Name: <input type="text" name="firstName" value="" required="required"> Last Name:  <input type="text" name="lastName" value="" required="required">	<br><br>
		Nickname:   <input type="text" name="nickName" value="" required="required">  Email:      <input type="text" name="email" value=""  required="required">	<br><br>
		Password:   <input type="password" name="password" value=""  required="required">  Repeat Password: <input type="password" name="repeatedPassword" value="" required="required">	  <br><br>
		<input type="submit" value="Submit">
	</form>
	
	
	<a href="/webapp-blog/">Go to homepage</a>
</body>

</html>