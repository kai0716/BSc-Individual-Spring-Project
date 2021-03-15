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
<c:set var="exam" value="${Exam}" />
<script>
$(document).ready(function() {
	setInterval(function(){
		window.location.reload();
	},1000*5)
	console.log("g");
})
	function AjaxSetMark(){
	var examID = "${exam.examID}";
	var student = $('#punish').val();
	 confirm("Are you sure to give zero mark for: " + student +"?")

			$.ajax({
	            async: false,
	            type: "POST",
	            url: "/teacher/Exam/ExamList/viewCheat/Punish",
	            data:  "&exam=" + examID +"&email=" + student,

	            success: function(response) {
	           
	            }
	        }) ;
	
}
</script>
<style><%@include file="../css/logout.css" %></style>	
<style>
.modal-body {
	height: 400px;
}

.modal-content {
	width: 700px;
}
.examTable{
column-width: 250px;
column-height: 200px;
text-align: center;
}
.title{
font-weight:bold;
font-size: 16px;

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
<div>
	<a href="/teacher/Exam/ExamList"><img src="/Img/back.jpeg" width="40" height="40"><label>Select Exam</label></a>
	<h1	style="padding-left:850px; font-size:40px;">Cheat List</h1>
</div>
<div>

<table class="table table-hover">
	<tr class ="title">
		<td class="examTable">Student Name</td>
		<td class="examTable">Last Leaving Time</td>
		<td class="examTable">Student Email</td>
		<td class="examTable">Leave Count</td>
		<td class="examTable">Punishment</td>
		
	</tr>
	
		<c:forEach var="cheat" items="${CheatList}">
		<tr class="examTable">
		    		<td class="examTable">${cheat.userName} is trying to cheat </td>
    				<td class="examTable">${cheat.cheatTime}</td>
					<td class="examTable">${cheat.userEmail}</td>
					<td class="examTable">${cheat.cheatCount}</td>
					<td class="examTable">
					   	<button type="button" class="btn btn-default" onclick="AjaxSetMark()" id="punish" value="${cheat.userEmail}">No Mark</button>
					</td>


    				
    	</tr>		
		</c:forEach>
		
	
</table>

</div>

</body>
</html>