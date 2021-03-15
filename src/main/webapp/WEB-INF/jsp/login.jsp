<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<meta charset="utf-8">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script><%@include file="../js/user.js" %></script>
<!-- enable ajax -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<title>Login</title>
</head>
<style>
.login{
width:600px;
height:460px;
margin: auto;
padding-top: 0px;
background-color: white;
border-radius: 25px;
}
body.body{
background-image:url('/Img/background.png');
}
.head{
padding-top:30px;
padding-bottom:130px;
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
margin:40px;
}
.form-group{
font-size:16px;
}
.button{
padding-left:250px;
}
</style>
<body class="body" >
<div class="head">
<p style="font-size:50px;" align="center">Online Exam and Management System</p>
</div>

<div class="login">
<form:form method ="POST" commandName="userLogin" action="/login"  onsubmit ="return loginAjax()">
	<div class="title">
		<h1 align="center"> Login</h1>
  	</div>
    <div id="error" class="error" ><font color="red"></font></div>
 
 <div class="form">
	 <div class="form-group" >
		 <form:label path="email" for="email">Email address</form:label>
		 <form:input id = "email" path="email" class="form-control" aria-describedby="emailHelp" placeholder="Enter email" cssErrorClass = "error"/>
		 <form:errors path="email"  cssClass="error" />
	 </div>
	 
	 <div class="form-group">
	 <form:label path="password">Password</form:label>
	 <form:password path="password" id ="password" class="form-control" aria-describedby="emailHelp" placeholder="Enter password" cssErrorClass = "error"/>
	 <form:errors path="password"  cssClass="error" /></td>
	 </div>
	 <a href="/Reset">Reset password</a>
 </div>
 
 <div class="button">
 	<input type="submit" value="Login" name="log_in"class="btn btn-primary" style="width:100px; height:50px; font-size:20px;"/>
 </div>
<div id="info" class="success"></div>

</form:form>

</div>

</body>
</html>




