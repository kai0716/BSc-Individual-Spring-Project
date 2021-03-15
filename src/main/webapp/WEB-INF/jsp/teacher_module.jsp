<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Insert title here</title>
</head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
<style><%@include file="../css/logout.css" %></style>		
<style>
.modal-body {
	height: 400px;
}

.modal-content {
	width: 700px;
}
.examTable{
border: 3px solid black;
column-width: 150px;
column-height: 200px;
text-align: center;
}
.title{
font-weight:bold;
font-size: 16px;

border: 3px solid black;
column-width: 150px;
column-height: 200px;
text-align: center;
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

<table class="examTable">
	<tr class ="title">
		<td class="examTable">Module Code</td>
		<td class="examTable">Module Name</td>
		<td class="examTable">Exam</td>
	</tr>
	
		<c:forEach var="module" items="${ModuleList}">
		<tr class="examTable">
    				<td class="examTable">${module.moduleCode}</td>
    				<td class="examTable">${module.moduleName}</td>
					
    				<form:form method ="POST" commandName="dto" action="/teacher/Exam/ExamList/viewCheat" >
    					<form:input  path="exam" type="hidden" value="${exam.examID}"/>
    					<td><input  type="submit" class="btn btn-primary" value ="View cheat" id="submit"/></td>
    				</form:form>
    	</tr>		
		</c:forEach>
		
	
</table>
</div>

</body>
</html>