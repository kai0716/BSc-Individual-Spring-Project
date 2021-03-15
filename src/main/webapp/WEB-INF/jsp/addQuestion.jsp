<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>
<script><%@include file="../js/addQuestion.js" %></script>
<title>Add question page</title>
</head>

<style><%@include file="../css/logout.css" %></style>	
<style>
.add{
  text-align: center;
  margin:0 auto;
}
.error{
color:red;
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
.form{
width:1200px;
  margin:0 auto;
}
.upload{
padding-left:530px;
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

<h1 align="center">Add Question Page</h1>
<!--  -->
<div class="form" >
<form:form method ="POST" commandName="AddQuestion" id="addQuestion_validation" action="/teacher/addQuestion">
  <div class="form-group">
    <form:label path="question" style="font-size:20px;">Question</form:label>
   	<form:input class="form-control"  path="question" id ="question" name="question"/>
  </div>

  <div class="form-group">
	<form:label path="choice" style="font-size:20px;">Choice 1</form:label>
    <form:input class="form-control" path="choice" id ="choice" name="choice"/>
  </div>
  
    <div class="form-group">
	<form:label path="choice" style="font-size:20px;">Choice 2</form:label>
    <form:input class="form-control" path="choice" id ="choice2" name="choice"/>
  </div>

	<tr>
    	<td><div id="addChoice"></div></td>
	</tr>
	
	  <input type="button" id="addRows" name="addRows" value="Add Option Rows" class="btn btn-secondary" style="font-size:18px;"/>
      <input type="button" id="removeRows" value="Delete Option Rows" class="btn btn-secondary" style="font-size:18px;"/> 
	
	<br/><br/><br/><br/>
  <div class="form-group">
	<form:label path="answer" style="font-size:20px;">Answer</form:label>
    <form:input class="form-control" path="answer" id ="answer" name="answer"/>
  </div>

	<tr>
    	<td><div id="addAnswer"></div></td>
	</tr>
	
	  <input type="button" id="addAnswerRows" name="addAnswerRows" value="Add Answer Rows" class="btn btn-secondary" style="font-size:18px;"/>
      <input type="button" id="removeAnswerRows" value="Delete Answer Rows" class="btn btn-secondary" style="font-size:18px;"/>   
	<br/><br/><br/><br/>	
  <div class="form-group">
	<form:label path="mark" style="font-size:20px;">Mark</form:label>
   	<form:select path="mark" id="mark" class="form-control">
   		<form:option value="1">1</form:option>
   		<form:option value="2">2</form:option>
   		<form:option value="3">3</form:option>
   		<form:option value="4">4</form:option>
   		<form:option value="5">5</form:option>
	</form:select>
  </div>
  <div class="form-group">
	<form:label path="module" style="font-size:20px;">Module</form:label>
   	<form:select path="module" id= "module" class="form-control">
        <c:forEach var="module" items="${Module}">
    				<option value="${module}">${module}</option>
		</c:forEach>
	</form:select>
  </div>
  

<div class="add">        
 <input type="submit" value="Add" name="add"  id="addQuestions" class="btn btn-primary" style="width:300px; height:50px; font-size:24px;"/>       
</div>          
</form:form>



<c:url value="/uploadExcelFile" var="uploadFileUrl" />
<form method="post" enctype="multipart/form-data" action="${uploadFileUrl}">
	<br/>
     <input type="file" name="file" accept=".xls,.xlsx" style="width:650px; height:50px; font-size:26px; margin:0 auto; padding-left:256px;"/>
	<div class="upload">
    <input type="submit" value="Upload file"   style="width:156px; height:45px; font-size:24px;"/>
    <br/><br/><br/></br>
    </div>
</form>
</div>

</body>
</html>