<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script><%@include file="../js/resultAjax.js" %></script>

<style><%@include file="../css/logout.css" %></style>
<style><%@include file="../css/teacherMainpage.css" %></style>		
<style>
.modal-body {
	height: 400px;
}

.modal-content {
	width: 800px;
}
.title{
	font-weight: bold;
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
<head>

    <title>Welcome Page</title>
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
				<li><a href="/teacher/" style="font-size:20px">Home</a></li>
			</ul>
			
			<ul class="logout">
				<li><font size="5px"color="white">${User} </font></li>
     			<li><a href="/logout" style="font-size:20px "><span class="glyphicon glyphicon-log-out"></span> Log Out</a></li>
 		 	</ul>
		</div>
	</div>
</nav>

<h1 align="center">Main Page</h1>


<div class="container">
<div class="row">
	<h3>Exam</h3>
	<div class="c1"></div>
	<div class="col-lg-4" >
		<a href="/teacher/Exam/addExam"><img src="/Img/addExam.png" width="150" height="150"></a>
		<h4 class="name1">Add Exam</h4>
	</div>	
	<div class="col-lg-4">
			<a href="/teacher/Exam"><img src="/Img/editExam.png" width="150" height="150"></a>
			<h4 class="name1">Edit Exam</h4>
	</div>
	
	<div class="col-lg-4">
		<a href="/teacher/Exam/ExamList"><img src="/Img/view.jpeg" width="150" height="150"></a>
		<h4 class="name1">View Cheat</h4>	
 	</div>	
</div>	
	
	<div class="row">
		<h3>Question</h3>
		<div class="col-lg-4" >
			<a href="/teacher/addQuestion"><img src="/Img/addQuestion.png" width="150" height="150"></a>
			<h4 class="name2">Add Question</h4>
		</div>
	</div>
	

	<div class="row">
		<h3>Result</h3>
		<div class="col-lg-4">
			<a href="/teacher/student"><img src="/Img/result.png" width="150" height="150"></a>
	 		<h4 class="name3">Student Result</h4>
		</div>
		
		<div class="col-lg-4">
	 		<a  data-toggle="modal" data-target="#myModal"><img src="/Img/totalMark.png" width="150" height="150"></a>
	 		<h4 class="name3">Total marks	</h4>
		</div>
	</div>
</div>

<div>
<%-- <a href="/teacher/Exam/ModifyDatabaseQuestion" class="btn btn-default">Modify Questions</a> --%>
</div>

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Module</h4>
				</div>
				<div class="modal-body">

						<div id="qustionTable">
							<table class="table table-hover">
								<tr class ="title">
									<td>#</td>
									<td>Module Code</td>
									<td>Module Name</td>
									<td class="examTable">Exam</td>
								</tr>
									
									<c:forEach var="module" items="${ModuleList}" varStatus="loop">
										<c:if test="${module.examList.size()==0}">
											<tr>
								    			<td>${loop.index}</td>
								    			<td>${module.moduleCode}</td>
								    			<td>${module.moduleName}</td>
												<td><h5> No exam </h5></td>
								   			 </tr>
								    	</c:if>	
										<c:if test="${module.examList.size() !=0}">
											<tr>
								    			<td>${loop.index}</td>
								    			<td>${module.moduleCode}</td>
								    			<td>${module.moduleName}</td>
												<td><button type="button" id="button" class="btn btn-default" data-toggle="modal" data-target="#myExamModal" onclick="AjaxAdd('${module.moduleCode}')">OverAll Result</button></td>
								   			 </tr>
								    	</c:if>
									</c:forEach>
							</table>

						</div>
				</div>
	
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="myExamModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Exam List</h4>
				</div>
				<div class="modal-body">

						<div id="qustionTable">
							<table id="resultTable" class="table table-hover">
								<tr class ="title">
									<td>#</td>
									<td>Exam Name</td>
									<td>Exam Average Mark </td>
									<td>Student number</td>
									<td>Release Result</td>
								</tr>
								<tbody id="tbody">
								
								</tbody>
						
							</table>

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
