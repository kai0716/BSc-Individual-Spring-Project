<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Question List</title>
</head>
<style><%@include file="../css/logout.css" %></style>	
<style>
table, th, td {
  border: 3px solid black;
  column-width: 150px;
  column-height: 200px;
   text-align: center;
}
.title{
font-weight:bold;
font-size: 16px;
}


.modal-body {
	height: 250px;
}

.modal-content {
	width: 700px;
}
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<body>
<h1>Modify question</h1>
<div>

<table>
	<tr class= "title">
		<td>Question</td>
		<td>Edit Question Information</td>
		<td>Edit Choice</td>
		<td>Edit Answer</td>
	</tr>
	
		<c:forEach var="question" items="${Teacher_Question}">
				<tr>
					<td>${question.question}</td>
    				<form:form method ="POST" commandName="dto" action="/teacher/Exam/ModifyDatabaseQuestion/Edit" >
    					<form:input  path="questionId" type="hidden" value="${question.questionID}"/>
    					<td class="examTable">
					   		<button type="button" class="btn btn-default" data-toggle="modal" data-target="#questionModal">Edit Question</button>
						</td>
						<td class="examTable">
					   		<button type="button" class="btn btn-default" data-toggle="modal" data-target="#choiceModal" > Edit Choice</button>
						</td>
						<td class="examTable">
					   		<button type="button" class="btn btn-default" data-toggle="modal" data-target="#answerModal">Edit Answer</button>
						</td>
    				</form:form>

					
				</tr>
		</c:forEach>

</table>
<a href="/success-login" class="btn btn-default">Go Back</a> <br /> <br />
<a href="/logout" class="btn btn-default">Log out</a>
</div>

  <div class="modal fade" id="questionModal" role="dialog">
   <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
         </div>
         <div class="modal-body">
            <div id="qustionTable">
               <form:form method ="POST" commandName="dto" action="/teacher/Exam/ModifyDatabaseQuestion/Edit">
               
                  <div class="form-group">
                     <form:label for="question" path="question" >Question</form:label>
                     <form:input class="form-control"  path="question" id ="question" />
                  </div>

                  <div class="form-group">
                     <form:label path="mark">Question mark</form:label>
                     <form:select path="mark" id="mark">
                        <form:option value="1">1 Mark</form:option>
                        <form:option value="2">2 Mark</form:option>
                        <form:option value="3">3 Mark</form:option>
                        <form:option value="4">4 Mark</form:option>
                        <form:option value="5">5 Mark</form:option>
                     </form:select>
                  </div>
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

<div class="modal fade" id="choiceModal" role="dialog">
   <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
         </div>
         <div class="modal-body">
            <div id="qustionTable">
               <form:form method ="POST" commandName="dto" action="/teacher/Exam/ModifyDatabaseQuestion/Edit">
                 
                 <c:forEach var="question" items="${Teacher_Question}">
                  <div class="form-group">
                     <form:label for="choice" path="optionList" >Question Choices</form:label>
                     <form:input class="form-control"  path="optionList" id ="choice" />
                  </div>
				</c:forEach>
                  <div class="form-group">
                     <form:label for="choice" path="optionList" >Question Choices</form:label>
                     <form:input class="form-control"  path="optionList" id ="choice" />
                  </div>

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

<div class="modal fade" id="answerModal" role="dialog">
   <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
         </div>
         <div class="modal-body">
            <div id="qustionTable">
               <form:form method ="POST" commandName="dto" action="/teacher/Exam/ModifyDatabaseQuestion/Edit">
                  <div class="form-group">
                     <form:label for="answer" path="answerList" >Question Answer</form:label>
                     <form:input class="form-control"  path="answerList" id ="answer" />
                  </div>

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
