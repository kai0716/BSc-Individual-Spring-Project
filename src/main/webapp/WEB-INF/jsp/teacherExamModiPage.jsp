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
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
<script><%@include file="../js/editExam.js" %></script>

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

function trigger_Date() {
	console.log($('#date_time').val());
	 $('#date_time').removeAttr("disabled");
	 $('#date_time').click();
	 
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
column-width: 150px;
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

<h1 align="center">Modify Exam</h1>
<div>

<table class="table table-hover">
	<tr class ="title">
		<td class="examTable">Module Code</td>
		<td class="examTable">Module Name</td>
		<td class="examTable">Exam Name</td>
		<td class="examTable">Exam Duration</td>
		<td class="examTable">Exam Start Time</td>
		<td class="examTable">Exam Status</td>
		<td class="examTable">Action </td>
		<td class="examTable">Edit Exam Detail</td>
		<td class="examTable">Modify Exam Question</td>
	</tr>
	
		<c:forEach var="exam" items="${Exams}">
		<tr class="examTable">
    				<td class="examTable">${exam.module.moduleCode}</td>
    				<td class="examTable">${exam.module.moduleName}</td>
    				<td class="examTable">${exam.examName}</td>
    				<td class="examTable">${exam.duration}</td>
    				<td class="examTable">${exam.startTime}</td>
    				<form:form method ="POST" commandName="dto" action="/teacher/Exam/status" >
    					<form:input  path="exam" type="hidden" value="${exam.examID}"/>
    					<c:if test="${exam.status == '1'}">
							<td class="examTable"><label>Open</label></td>
							<form:input  path="open_close" type="hidden" value="open"/>
							<td class="examTable"><input class="btn btn-danger" type="submit" value ="Close"/></td>
    					</c:if>
    					<c:if test="${exam.status == '0'}">
    					
    						<td class="examTable"><label>Closed</label></td>
    						<form:input  path="open_close" type="hidden" value="close"/>
    						<td class="examTable"><input  type="submit" class="btn btn-success" value ="Open"/></td>
    					</c:if>
    				</form:form>
    				
    				
    				<td class="examTable">
					   <button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="editAjaxPost(${exam.examID})">Edit</button>
					</td>
					
    				<form:form method ="GET" commandName="dto" action="/teacher/Exam/ModifyExamQuestion" >
    					<form:input  path="exam" type="hidden" value="${exam.examID}"/>
    					<td><input  type="submit" class="btn btn-primary" value ="Add Question" id="submit"/></td>
    				</form:form>
    	</tr>		
		</c:forEach>
		
	
</table>	
</div>

  <div class="modal fade" id="myModal" role="dialog">
   <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
         </div>
         <div class="modal-body">
            <div id="qustionTable">
               <form:form method ="POST" commandName="dto" action="/teacher/Exam/ModifyExamQuestion/Edit">
                  <div class="form-group">
                     <form:label for="examName" path="examName" >Exam Name</form:label>
                     <form:input class="form-control"  path="examName" id ="examName" />
                  </div>
                  <div class="form-group">
                     <div   class='input-group date' id='datetimepicker1'>
                        <form:label for="startTime" path="startTime" >Exam Start Time</form:label>
                        <form:input path="startTime" type='text' class="form-control"  disabled="true" id="date_time" placeholder="Please Click the Clendar Icon to Change Date (DO NOT CHANGE THE DATE FORMAT)" value="13"/>
                        <span class="input-group-addon" onclick="trigger_Date()">
                        <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                     </div>
                  </div>
                  <div class="form-group">
                     <form:label path="duration">Exam Duration</form:label>
                     <form:select path="duration" id="examDuration">
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
                  <form:input  path="exam" type="hidden" id="setExamID"/>
                  <input  type="submit" class="btn btn-primary" value ="Submit"/>
               </form:form>
            </div>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
         </div>
      </div>
   </div>
</div>
</body>
</html>