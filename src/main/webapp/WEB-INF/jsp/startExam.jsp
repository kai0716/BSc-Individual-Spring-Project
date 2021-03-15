<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<style>
.logout{
float:right;
}
#NEXT{
 display:block;
}
#SUBMIT{
 display:none;
}
.question{
 width:1200px;
  margin: auto;
  padding-top: 0px;
}
input[type=checkbox] {
  transform: scale(1.5);
}
.boarder{
border-bottom:2px;
border-bottom-style:inset;
width:1200px;
}
.choice{
padding-right:400px;
}

</style>
<script defer src="https://use.fontawesome.com/releases/v5.8.1/js/all.js" integrity="sha384-g5uSoOSBd7KkhAMlnQILrecXvzst9TdC09/VM+pjDTCM+1il8RHz5fKANTFFb+gQ" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script><%@include file="../js/timer.js" %></script>
<c:set var="duration" value="${Duration}" />
<c:set var="user" value="${User}" />
<c:set var="exam" value="${Exam}" />
<script>
window.addEventListener("load", myInit, true); function myInit(){ 
	
	startTimer(${duration});
	
}; 

$(document).ready(function() {
	var chance=0;
	function userCheated() {
	    // The user cheated by leaving this window (e.g opening another window)
	    // Do something
	    
		alert("You can't leave the Exam!");
	    document.getElementById("change").click();
	}
	window.onblur = userCheated;
	
var submit = "${Submit}";
var previous = "${Previous}"

if(submit =="submit"){
	$("#SUBMIT").css('display', 'block');
	$("#NEXT").css('display', 'none');
}
if(previous ==1){
	$("#PREVIOUS").css('display', 'none');
}
else{
	$("#PREVIOUS").css('display', 'block');
}

})

$(document).ready(function() {
<c:forEach items="${SOList}" var="option">
console.log("${option}");
$("#"+"${option}").prop('checked', true); 
</c:forEach>
})
var count=0;
function leave(){
	var userEmail = "${user.email}";
	var userName = "${user.firstName} " + "${user.lastName}";
	var examId= "${exam.examID}";
	var today = new Date().getTime();
	count++;
    $.ajax({
        async: false,
        type: "POST",
        url: "/student/Exam/ExamList/saveCheat",
        data: "&email=" + userEmail + "&userName=" + userName + "&exam=" + examId + "&cheatDate=" + today + "&count=" + count,

        success: function(response) {
            if (response.status == "SUCCESS") {
            }


        }
    });
}
</script>

<head>

    <title>Exam Page <></title>
</head>
<body>

<div class="question">
<div class="boarder">
<h1 align="center">${Module} ${ExamName} </h1>
<div id="centeredTime" align="center" style="font-size:30px;"><p id="timer"/></div>
</div>
	<h1>Question ${QuestionNumber}: ${Question}</h1>
	<label>Please select ${AnswerSize} options</label>
	
	
	<form:form method ="POST" commandName="dto" action="/student/Exam/start">
			<form:input  path="duration" type="hidden" id="duration"/>
	<div class="choice">	
			<ol>
			<c:forEach items="${OptionList}" var="option">
				<li>
				<form:checkbox id="${option.getChoice()}" name="Option" path="optionList"  value="${option.getChoice()}" style="font-size:20px"/><label style="font-size:24px">&nbsp&nbsp ${option.getChoice()}</label>
				</li>
			</c:forEach>
			</ol>
	</div>	
			<div class="container">
			  <div class="row">
			    <div class="col-lg-6">
					<button type="submit" value="Previous" name="previous" id="PREVIOUS" class="btn btn-warning"><i class="fas fa-arrow-left"></i></button>
			    </div>
			    <div class="col-lg-6">
			    	<button type="submit" value="Next" name="next" id="NEXT" class="btn btn-success"><i class="fas fa-arrow-right"></i></button>
					<button type="submit" value="Submit" name="submit" id="SUBMIT" class="btn btn-primary"><i class="fas fa-arrow-right"></i></button>
			    </div>
			  </div>
			</div>
			
			<form:input  path="exam" type="hidden" value ="${exam.examID}"/>
	</form:form>

	<button class="regular" name="change" hidden="hidden" id="change" onclick="leave()"></button>
</div>

</body>
</html>
