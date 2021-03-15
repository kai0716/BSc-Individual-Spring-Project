<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>	
<script><%@include file="../js/createUser.js" %></script>

<style><%@include file="../css/logout.css" %></style>	
<style>
.navbar {
  min-height: 80px;
}

.navbar-brand {
  padding: 0 15px;
  height: 80px;
  line-height: 80px;
  font-size:25px;
}

.navbar-toggle {
  /* (80px - button height 34px) / 2 = 23px */
  margin-top: 23px;
  padding: 9px 10px !important;
}

@media (min-width: 768px) {
  .navbar-nav > li > a {
    /* (80px - line-height of 27px) / 2 = 26.5px */
    padding-top: 26.5px;
    padding-bottom: 26.5px;
    line-height: 27px;
  }
}
.form{
width:1200px;
  margin:0 auto;
}
.add{
  text-align: center;
  margin:0 auto;
}
.upload{
padding-left:530px;
}
</style>

<title>Create new User</title>
</head>


<body>
<nav class="navbar navbar-inverse">
	<div class="container-fluid">
	
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#alignment-example" aria-expanded="false">
			</button>
		<a class="navbar-brand" href="#">Online Exam</a>
		</div>
		
		<div class="collapse navbar-collapse" id="alignment-example">
			<ul class="nav navbar-nav">
				<li><a href="/success-login" style="font-size:20px">Home</a></li>
			</ul>
			<ul class="logout">
				<li><font size="5px"color="white">${User} </font></li>
     			<li><a href="/logout" style="font-size:20px "><span class="glyphicon glyphicon-log-out"></span> Log Out</a></li>
 		 	</ul>
		</div>
	</div>
</nav>
<h1 align="center">Add User</h1>

<div class="form">
<form:form method ="POST" commandName="addNewUser" action="/admin/createUser" id="register_validation" name="register_validation">

 	 <div class="form-group">	
			<form:label path="email" style="font-size:20px;">Email</form:label>
			<form:input class="form-control" id = "email" path="email" cssErrorClass = "error"/>
	</div>
	
	<div class="form-group">
			<form:label path="firstName" style="font-size:20px;">First name</form:label>
			<form:input class="form-control" id = "firstName" path="firstName" cssErrorClass = "error"/>
	</div>
	
	<div class="form-group">	
			<form:label path="lastName" style="font-size:20px;">Last Name</form:label>
			<form:input class="form-control" id = "lastName" path="lastName" cssErrorClass = "error"/>
	</div>
	
	<div class="form-group">
			<form:label path="userType" style="font-size:20px;">User type</form:label>
	
    		<form:select path="userType" id ="userType" class="form-control">
   			<c:forEach var="userType" items="${userTypeValues}">
    				<option value="${userType}">${userType}</option>
			</c:forEach>
			</form:select>
	</div>
	
	<div class="form-group">
			<form:label path="module" style="font-size:20px;">Module Code</form:label>

        	<form:select id="module" path="module" class="form-control">
        	<c:forEach var="module" items="${ModuleCode}">
    				<option value="${module}">${module}</option>
			</c:forEach>
        	</form:select>
	</div>

			<div id="addInput"></div>

     		<input type="button" id="addRows" name="addRows" value="Add Module" class="btn btn-secondary" style="font-size:18px;"/>
			<input type="button" id="removeRows" value="Delete Module" class="btn btn-secondary" style="font-size:18px;"/>

			<div class="add">
        	<input type="submit" value="Create Account" id="addUser"class="btn btn-primary" style="width:300px; height:50px; font-size:24px;"/>
			</div>
</form:form>


<c:url value="/uploadExcelFile" var="uploadFileUrl" />
<form method="post" enctype="multipart/form-data" action="${uploadFileUrl}">
	<br/>
     <input type="file" name="file" accept=".xls,.xlsx" style="width:650px; height:50px; font-size:26px; margin:0 auto; padding-left:256px;"/>
	<div class="upload">
    <input type="submit" value="Upload file"   style="width:156px; height:45px; font-size:24px;"/>
    </div>
</form>	
</div>


</body>
</html>