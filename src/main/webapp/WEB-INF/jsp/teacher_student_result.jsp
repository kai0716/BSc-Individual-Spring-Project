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
<script>
$(document).ready(function() {
	<c:forEach var="question" items="${QuestionList}"> 
	var mark=0;
	var mark_wrong=0;
	var question_mark = "${question.question_marks}";
	<c:forEach var="choice" items="${question.choiceList}">
		<c:forEach var="selected" items="${question.selectedChoiceList}">
			<c:forEach var="answer" items="${question.answer}">
		
			<c:if test="${choice.choice == answer.answer}">
			$("#${choice.optionId}").css('color', '#20bff9');
			$("#${choice.optionId}").css('font-weight', 'bold');
			</c:if>	
				
				<c:if test="${choice.choice == selected.choice}">
				
					<c:if test="${selected.choice == answer.answer}">
						$("#${choice.optionId}").css('color', '#02d15f');
						$("#${choice.optionId}").css('font-weight', 'bold');
						mark = mark+ (question_mark/"${question.answer.size()}");
						
						console.log(mark+"/"+question_mark);
					</c:if>
					
					<c:if test="${selected.choice != answer.answer}">
						$("#${choice.optionId}").css('color', '#f94558');
						$("#${choice.optionId}").css('font-weight', 'bold');
					</c:if>
					
					<c:if test="${question.selectedChoiceList.size() > question.answer.size()}">
						mark=0;
					</c:if>

				</c:if>
				var id= "a"+"${question.questionID}";
				document.getElementById(id).innerHTML= "Mark: "+mark+"/"+question_mark;
			</c:forEach>
		</c:forEach>
	</c:forEach>
		
	</c:forEach>
});

</script>
<style><%@include file="../css/teacher_student_result.css" %></style>
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
<div class="image"><img src="/Img/anwerDescription.png" width="300" height="200"></div>
<a href="/teacher/student"><img src="/Img/back.jpeg" width="30" height="30"><label>Select Student</label></a>
<h1 class="title" align="center">${Name}'s Result </h1>

<div>
<div>
		<ol>
		<c:forEach var="question" items="${QuestionList}" varStatus="loop">
				<div class="question">
					
					<h2>${loop.index+1}.${question.question}</h2>
					
					<ol>
						<c:forEach var="choice" items="${question.choiceList}">
								<li class="choice" id="${choice.optionId}">${choice.choice}</li>
						</c:forEach>	
					</ol>
					
						<label class="mark_s" id="a${question.questionID}"></label>
				
				</div>

		</c:forEach>
		</ol>
		<h2 id="totalMark">Total Mark: ${MarkGet} / ${TotalMark} </h2>
</div>


</div>

</body>
</html>