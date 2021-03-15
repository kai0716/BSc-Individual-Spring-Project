<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script><%@include file="../js/resultAjax.js" %></script>
    <title>Exam List</title>
</head>

<style><%@include file="../css/logout.css" %></style>	
<style>
table, th, td {
  column-width: 150px;
  column-height: 200px;
   text-align: center;
}
.title{
font-weight:bold;
font-size: 16px;
}
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
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
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

<h1 align="center">Start exam</h1>
<div>

<table class="table table-hover">
	<tr class= "title">
		<td>Module Code</td>
		<td>Module Name</td>
		<td>Exam Name</td>
		<td>Exam Duration</td>
		<td>Exam Start Time</td>
		<td>Exam Status</td>
	</tr>
	
		<c:forEach var="exam" items="${Exams}">
		<tr>
    				<td>${exam.module.moduleCode}</td>
    				<td>${exam.module.moduleName}</td>
    				<td>${exam.examName}</td>
    				<td>${exam.duration}</td>
    				<td>${exam.startTime}</td>
    				<c:if test="${exam.status == '1'}">
    				<td>
    				<form:form method ="GET" commandName="dto" action="/student/Exam/start" >
    					<form:input  path="module" type="hidden" value="${exam.module.moduleCode}"/>
    					<form:input  path="exam" type="hidden" value="${exam.examID}"/>
    					<input  type="submit" class="btn btn-default" value ="Start"/>
    				</form:form>
    				</td>
    				</c:if>
    				<c:if test="${exam.status == '0'}">
    				<td><label>Close</label></td>
    				</c:if>
    	</tr>		
		</c:forEach>
		
	
</table>
</div>
</body>
</html>
