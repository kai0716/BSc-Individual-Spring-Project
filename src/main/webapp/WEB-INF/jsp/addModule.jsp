<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<html>
<head>
    <title>Welcome Page</title>
</head>

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
.upload{
padding-left:890px;
margin:0 auto;
}
</style>

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

<h1 align="center">Add Module</h1>
<div>

<c:url value="/uploadExcelFile" var="uploadFileUrl" />
<form method="post" enctype="multipart/form-data"
  action="${uploadFileUrl}">

    <input type="file" name="file" accept=".xls,.xlsx" style="width:650px; height:50px; font-size:26px; margin:0 auto; padding-left:256px;"/>
      <div class="upload">	 
    <input type="submit" value="Upload File" style="width:156px; height:45px; font-size:24px; " />
    </div>
</form>	

</div>
</body>
</html>