<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Add Exam Page</title>
</head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
<script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>
<script><%@include file="../js/category2.js" %></script>
<script><%@include file="../js/addExam.js" %></script>
<script type="text/javascript">
$(document).ready(function () {
	console.log("aaa")
	$('#datetimepicker1').datetimepicker();
	
	$('#date_time').on('change', function() {
		$('#datetimepicker1').datetimepicker({
			   // dateFormat: 'dd-mm-yy',
	        dateFormat: 'yy-mm-dd', 
	        timeFormat: 'HH:mm' 
			});
		});
});
</script>

<style><%@include file="../css/logout.css" %></style>	
<style>
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
.form{
width:1200px;
  margin:0 auto;
}
}
.add{
  text-align: center;
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

<h1 align="center">Add Exam page</h1>
<div>

<br/>
<br/>
<div class="form">
<form:form method ="POST" commandName="dto" id="addExam_validation" action="/teacher/Exam/addExam">
  <div class="form-group">
	<form:label path="module" style="font-size:20px;">Module</form:label>
	<form:select path="module" id="module" class="form-control">
	<c:forEach var="module" items="${Modules}">
	<tr>
		<td id="module">                      
              <form:option value="${module.moduleCode}">${module.moduleCode}</form:option>
        </td>
	</tr>
    </c:forEach> 
    </form:select>
  </div>

  <div class="form-group">
    <form:label path="examName" for="examName" style="font-size:20px;">Exam Name</form:label>
   	<form:input class="form-control"  path="examName" name="examName" id ="examName"/>
  </div>


  <div class="form-group">
       <form:label path="duration" for="examDuration" style="font-size:20px;">Exam Duration</form:label>
       <form:select path="duration" id="examDuration" class="form-control">
	       <form:option value="0.5">30 Minutes</form:option>
	       <form:option value="1">1 Hour</form:option>
	       <form:option value="1.5">1.5 Hour</form:option>
	       <form:option value="2">2 Hour</form:option>
	       <form:option value="2.5">2.5 Hour</form:option>
	       <form:option value="3">3 Hour</form:option>
	       <form:option value="3.5">3.5 Hour</form:option>
	       <form:option value="4">4 Hour</form:option>
       </form:select>
  </div>

	<form:label for="startTime" path="startTime" style="font-size:20px;">Exam Start Time</form:label>
   <div class='input-group date' id='datetimepicker1'>
       <form:input path="startTime" type='text' class="form-control"  id="date_time" placeholder="MM/DD/YYYY HH:MM PM  (Use this format)"/>
       <span class="input-group-addon">
       <span class="glyphicon glyphicon-calendar"></span>
       </span>
  </div>
  <br/></br>
  <br/>

	<div class="add">
 	<input type="submit" value="Add" name="add"  id="addExam" class="btn btn-primary" style="width:300px; height:50px; font-size:24px;"/> 
 	</div>
 </form:form> 
</div>

<br/>
<br/>
</div>


</body>
</html>