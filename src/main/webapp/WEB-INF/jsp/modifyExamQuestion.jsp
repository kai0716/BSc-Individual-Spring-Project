<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>Modify Exam question</title>
</head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script defer src="https://use.fontawesome.com/releases/v5.8.1/js/all.js" integrity="sha384-g5uSoOSBd7KkhAMlnQILrecXvzst9TdC09/VM+pjDTCM+1il8RHz5fKANTFFb+gQ" crossorigin="anonymous"></script>	
<script><%@include file="../js/category2.js" %></script>

<style><%@include file="../css/logout.css" %></style>	
<style><%@include file="../css/category.css" %></style>	
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
	<a href="/teacher/Exam"><img src="/Img/back.jpeg" width="30" height="30"><label>Modify Exam </label></a>
	<h1 align="center"><a data-toggle="modal" data-target="#myModal"><img src="/Img/add.png" width="50" height="50"></a>Add Exam Questions</h1>
	</div>
	<div>

		<table class="table table-hover">
			<tr class="title">
				<td>Question</td>
				<td>Delete</td>

			</tr>

			<c:forEach var="question_list" items="${Exam_Question_List}">
				<tr>
					<td>${question_list.question}</td>
    				<form:form method ="POST" commandName="dto" action="/teacher/Exam/ModifyExamQuestion/Delete" >
    					<form:input  path="questionId_for_delete" type="hidden" value="${question_list.questionID}"/>
    					<form:input path ="exam" type="hidden" value="${Exam.examID}"/>
    					<td><input type="submit" class="btn btn-danger" value="Delete" /></td>
    				</form:form>
					
				</tr>
			</c:forEach>
		</table>


		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Tick the questions you want to add</h4>
					</div>

					<div class="modal-body">

							<div id="qustionTable">
								<table class="table table-hover">
									<tr class="title">
										<td class="title">Question</td>
									</tr>
									
									<tbody id ="table_body_content">
										<c:forEach var="question_list" items="${All_Question}">
										<tr>
											<td id="question">
													<input type="checkbox" class= "question_checkbox_value" id ="question_checkbox_value" value="${question_list.question}" />
													<input id= "examID" type="hidden" value="${Exam.examID}"/>
													<label>&nbsp;&nbsp; ${question_list.question}</label>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div id="category">
							
								<div class="filter">
      							<input type="text" placeholder="Search.." name="search" id ="search" style="height: 30px;" >
      							<button type="submit" onclick="Search()" id="button"><i class="fas fa-search"></i></button>
      							   
								<div class="reset">    	
								<input type="button" onclick="ResetCategory()" class="btn btn btn-warning" value="Reset" style="width:100px"/>
								</div>  
      							</div>
      							
      							<div class="filter">
								<label >&nbsp;&nbsp; True or False</label>
								<input type="checkbox" id ="tof" name="tof"/>
								</div>

								<div class="filter">
								<form>								
									<label>&nbsp;&nbsp; Two option</label>
									<input type="radio" id="two_option" name="options" value="two_option" class="custom-control-input"/>

									<label>&nbsp;&nbsp; Three option </label>
									<input type="radio" id="three_option" name="options" value="three_option" class="custom-control-input"/>								

									<label>&nbsp;&nbsp; Four option</label>
									<input type="radio" id="four_option" name="options" value="four_option" class="custom-control-input"/>
								
									<label>&nbsp;&nbsp; Five option</label>
									<input type="radio" id="five_option" name="options" value="five_option" class="custom-control-input"/>
								</form>
								</div>
								
								<div class="filter">
								<label id ="getMark">&nbsp;&nbsp; Mark: All</label> 
								<input type="range" min="0" max="5" value="0" id="mark" />
								</div>
								
								<div class="filter">
								<label>&nbsp;&nbsp; Sorting </label>
								<select onclick="Sort()" id="select">
									  <option value="least"  >Most Use => Least Use </option>
									  <option value="most">Least Use => Most Use</option>
								</select>
								</div>
							
							<div class="add">
									<form:form method ="GET" commandName="dto" action="/teacher/Exam/ModifyExamQuestion" >
									<form:input path ="exam" type="hidden" value="${Exam.examID}"/>
									<input type="submit" onclick="AjaxAdd()"class="btn btn btn-primary btn-lg" value="Add" style="width:200px"/>
									</form:form>
							</div>
							<div>

							</div>
							</div>
							
					</div>
	
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>
