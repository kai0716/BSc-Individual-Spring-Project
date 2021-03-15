<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>
<script><%@include file="../js/user.js" %></script>
<!-- enable ajax -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<title>Login</title>
</head>
<style>
.login{
width:600px;
height:630px;
margin: auto;
padding-top: 0px;
background-color: white;
border-radius: 25px;
}
.head{
padding-top:30px;
padding-bottom:50px;
}
.title{
padding-top:10px;
padding-bottom:10px;
border-bottom;
border-bottom:2px;
border-bottom-style:inset;
background-color:#E0E0E0;
}
.form{
margin:30px;
}
.form-group{
font-size:16px;
}
.button{
padding-left:250px;
}
.error{
color:red;
font-size:14px;
}
body.body{
background-image:url('/Img/background.png');
}
</style>
<body class="body" >
<div class="head">
<p style="font-size:50px;" align="center">Online Exam and Management System</p>
</div>

<div class="login">
<form:form method ="POST" commandName="dto" action="/reset/password" id="resetPass">
	<div class="title">
		<h1 align="center"> Change password</h1>
  	</div>
    <div id="error" class="error" ><font color="red"></font></div>
 
 <div class="form">
	 <div class="form-group" >
		 <form:label path="email" for="email">Email address</form:label>
		 <form:input id = "email" path="email" class="form-control" aria-describedby="emailHelp" placeholder="Enter email" cssErrorClass = "error"/>
		 <form:errors path="email"  cssClass="error" />
	 </div>
	 
	 <div class="form-group">
	 <form:label path="oldPassword">Old Password</form:label>
	 <form:password path="oldPassword" id ="oldPassword" class="form-control" aria-describedby="emailHelp" placeholder="Enter Old Password" cssErrorClass = "error"/>
	 <form:errors path="oldPassword"  cssClass="error" /></td>
	 </div>
	 
	 <div class="form-group">
	 <form:label path="password">New Password</form:label>
	 <form:password path="password" id ="password" class="form-control" aria-describedby="emailHelp" placeholder="Enter password" cssErrorClass = "error"/>
	 <form:errors path="password"  cssClass="error" /></td>
	 </div>
	 
	 <div class="form-group">
	 <form:label path="password1">Confirm Password</form:label>
	 <form:password path="password1" id ="password1" class="form-control" aria-describedby="emailHelp" placeholder="Enter password" cssErrorClass = "error"/>
	 <form:errors path="password1"  cssClass="error" /></td>
	 </div>

 	<a href="/" style="font-size:18px">Log in</a>
 </div>
 
 <div class="button">
 	<input type="submit" value="Change" class="btn btn-primary" style="width:100px; height:50px; font-size:20px;"/>
 	
 </div>
<div id="info" class="success"></div>

</form:form>

</div>

</body>
</html>




