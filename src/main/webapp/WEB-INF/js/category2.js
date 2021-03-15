var tof_question =new Array();
var all_question = new Array();
var current_question = new Array();

var two_option = new Array();
var three_option = new Array();
var four_option = new Array();
var five_option = new Array();

var one_mark = new Array();
var two_mark = new Array();
var three_mark = new Array();
var four_mark = new Array();
var five_mark = new Array();
var append_question;

var secondClick = false;

var cate1 = new Array();
var cate2 = new Array();
var cate3 = new Array();
var cate1_status=0; //0=unchecked  1 =checked 
var cate2_status=0;
var cate3_status=0;
function AjaxAdd(){
	var  question_checkbox_value = new Array();
	 $.each($("input[class='question_checkbox_value']:checked"), function(){            
		 question_checkbox_value.push($(this).val());
     });
	console.log(question_checkbox_value+"ddd");
	var  examID = document.getElementById("examID").value;
	console.log(examID+"S");
		$.ajax({
            async: false,
            type: "POST",
            url: "/teacher/Exam/ModifyExamQuestion/Add",
            data: "&exam_Qestion_List=" + question_checkbox_value + "&exam=" + examID,

            success: function(response) {
           
            }
        }) ;
	}

function Search(){
	var search_message = $("#search").val();
	var examID = $("#examID").val();
	
	var send = false;
	$.ajax({
		async : false,
		type : "GET",
		url : "/teacher/Exam/ModifyExamQuestion/Category",
		data: {search: search_message, examId: examID},

		success : function(response) {
			console.log(response);
			if(response.length !=0){
				$('#table_body_content').empty();
				for(i=0; i<response.length; i++){
					showTable(response[i]);
					
					if ($("#tof").prop("checked") == true) {
						$("#tof"). prop("checked", false);
						$(".custom-control-input").removeAttr("disabled");
					}

					$(".custom-control-input"). prop("checked", false);
					$("#tof").removeAttr("disabled");
					document.getElementById('mark').value=0;
					document.getElementById('getMark').innerHTML = "&nbsp;&nbsp; Mark: All";
					
					cate1 = new Array();
					cate2 = new Array();
					cate3 = new Array();
					cate1_status=0;
					cate2_status=0;
					cate3_status=0;
				}
			}
			else{
				alert("Search not found");
			}
			
		}
	
	})
}

function ResetCategory(){
	$('#table_body_content').empty();
	current_question =new Array();
	current_question = all_question;
	for(var i =0; i<all_question.length; i++){
		showTable(all_question[i].content);
	}	
	if ($("#tof").prop("checked") == true) {
		$("#tof"). prop("checked", false);
		$(".custom-control-input").removeAttr("disabled");
	}

	$(".custom-control-input"). prop("checked", false);
	$("#tof").removeAttr("disabled");
	document.getElementById('mark').value=0;
	document.getElementById('getMark').innerHTML = "&nbsp;&nbsp; Mark: All";
	
	cate1 = new Array();
	cate2 = new Array();
	cate3 = new Array();
	cate1_status=0;
	cate2_status=0;
	cate3_status=0;

}



$(document).ready(function() {
	// -------------- Category list： True or False--------------------------------------------
	<c:forEach var="question_list" items="${All_Question}"> 
	var question = new Object();
	question.content = "${question_list.question}";
	question.usage = "${question_list.usage}"
		current_question.push(question);
	all_question.push(question);
	
			var choice = "${question_list.choiceList[0].choice}";
			
			if(choice == "True" || choice == "False"){
			  tof_question.push(question);
			}
	
	</c:forEach>
	
	$('input[name=tof]').change(function() {
		cate1_status=1;
		
		if($("#tof").prop("checked") == true) {
			cate1 =tof_question;
			category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
			$(".custom-control-input").attr("disabled", true);
		}
		else{
			$(".custom-control-input").removeAttr("disabled");
			cate1 =all_question;
			category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		}
	});
	
	// -------------- Category list： Number of question options--------------------------------------------		
	<c:forEach var="question_list" items="${All_Question}"> 
	var question = new Object();
	question.content = "${question_list.question}";
	question.usage = "${question_list.usage}"
	

		var choice_list_length="${fn:length(question_list.choiceList)}";
			console.log("${fn:length(question_list.choiceList)}");
			
			if(choice_list_length-1==1){
				  two_option.push(question);
			}
			if(choice_list_length-1==2){
				  three_option.push(question);
			}
			if(choice_list_length-1==3){
				  four_option.push(question);
			}
			if(choice_list_length-1==4){
				  five_option.push(question);
			}
	</c:forEach>
	
	$(".custom-control-input").click(function() {
		cate2_status = 1;
		
		$("#tof").attr("disabled", true);
		var radio_value= $('input[name=options]:checked').val();
		
		if(radio_value == "two_option"){
			cate2= two_option;
			category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		}
		if(radio_value == "three_option"){
			cate2= three_option;
			category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		}
		if(radio_value == "four_option"){
			cate2= four_option;
			category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		}
		if(radio_value == "five_option"){
			cate2= five_option;
			category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		}
});
	
	// -------------- Category list： Marks--------------------------------------------	
	<c:forEach var="question_list" items="${All_Question}"> 
	var question = new Object();
	question.content = "${question_list.question}";
	question.usage = "${question_list.usage}"
	
		var marks= "${question_list.question_marks}";
			if(marks ==1){
				one_mark.push(question);
			}
			if(marks ==2){
				two_mark.push(question);
				}
			if(marks ==3){
				three_mark.push(question);
				}
			if(marks ==4){
				four_mark.push(question);
			}
			if(marks ==5){
				five_mark.push(question);
			}
	</c:forEach>
	
	$("#mark").change(function() {
		cate3_status =1;
		
		var x = document.getElementById("mark").value;
		 if(x ==0){
			 document.getElementById("getMark").innerHTML = "&nbsp;&nbsp; Mark: All";
			 cate3= all_question;
			 category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		 }
		 if(x==1){
			 document.getElementById("getMark").innerHTML = "&nbsp;&nbsp; Mark: "+x;
			 cate3= one_mark;
			 category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		 }
		 if(x==2){
			 document.getElementById("getMark").innerHTML = "&nbsp;&nbsp; Mark: "+x;
			 cate3= two_mark;
			 category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		 }
		 if(x==3){
			 document.getElementById("getMark").innerHTML = "&nbsp;&nbsp; Mark: "+x;
			 cate3= three_mark;
			 category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		 }
		 if(x==4){
			 document.getElementById("getMark").innerHTML = "&nbsp;&nbsp; Mark: "+x;
			 cate3= four_mark;
			 category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		 }
		 if(x==5){
			 document.getElementById("getMark").innerHTML = "&nbsp;&nbsp; Mark: "+x;
			 cate3= five_mark;
			 category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status);
		 }
	})
	
	
	
});


function category(cate1, cate2, cate3, cate1_status, cate2_status, cate3_status){
//	if(cate3.length== 0){
//		$('#table_body_content').empty();
//		 for(var i =0; i<cate3.length; i++){
//				showTable(cate3[i]);
//		}
//	}
	//---------------Single selection-------------------------
		if(cate1_status-1 == 0){
			console.log(cate1+"avd");
			$('#table_body_content').empty();
			current_question =new Array();
			 for(var i =0; i<cate1.length; i++){
					showTable(cate1[i].content);
			}
		}
		if(cate2_status-1 == 0){
			$('#table_body_content').empty();
			current_question =new Array();
			 for(var i =0; i<cate2.length; i++){
					showTable(cate2[i].content);
			}
		}
		if(cate3_status-1 == 0){
			$('#table_body_content').empty();
			current_question =new Array();
			 for(var i =0; i<cate3.length; i++){
					showTable(cate3[i].content);
			}
		}
	//--------------Two selection-------------------------------	
		if(cate1_status-1 == 0 && cate3_status-1 == 0){
			var cate1_cate3 = category1(cate1, cate3);
			
			$('#table_body_content').empty();
			current_question =new Array();
			 for(var i =0; i<cate1_cate3.length; i++){
					showTable(cate1_cate3[i].content);
			}
		}
		if(cate2_status-1 == 0 && cate3_status-1 == 0){
			cate2_cate3 = category1(cate2, cate3);
			
			$('#table_body_content').empty();
			current_question =new Array();
			 for(var i =0; i<cate2_cate3.length; i++){
					showTable(cate2_cate3[i].content);
			}
		}
}

function category1(c1, c2){
	var multi_cate_list = new Array();
	 for(var i =0; i<c1.length; i++){
			for(var k=0; k<c2.length; k++){
				if(c1[i].content == c2[k].content){
					//this need to be an object
					multi_cate_list.push(c1[i]);
				}
			}
	 }
	return multi_cate_list;
}
function addToCurrentList(question){
	
	current_question.push(question);
	showTable(question.content);
}


function showTable(question){
	var add_td = document.getElementById("table_body_content");
	append_question= question;
	   $('<tr><td id="question">'+
				 '<ol>'+
					'<input id="question_checkbox_value" class="question_checkbox_value" type="checkbox"  value="'+append_question+'" />'+
					'<input id = "examID" type="hidden" value="${Exam.examID}" />' +
					'<label>&nbsp;&nbsp; '+append_question+'</label>'+
				 '</ol>'+
		  '</td></tr>').appendTo(add_td);
}

function Sort(){
	var selectVal= $('#select').val();
	console.log(selectVal);
	if(selectVal =="most"){
		var list =bubble(current_question);
		console.log(list);
		$('#table_body_content').empty();
		for(var i=0; i<list.length; i++){
			showTable(list[i].content);
		
		}
	}
	if(selectVal =="least"){
		var list = bubble2(current_question);
		$('#table_body_content').empty();
		for(var i=0; i<list.length; i++){
			showTable(list[i].content);console.log("ad");
		}
	}
}

/*    Title: Bubble sort source code
*    Author: Popovich, A
*    Date: 2019
*    Availability: https://stackoverflow.com/questions/37817334/javascript-bubble-sort
*/ 
function bubble(question) {
    var len = question.length;
  
    for (var i = 0; i < len ; i++) {
      for(var j = 0 ; j < len - i - 1; j++){ // this was missing
      if (question[j].usage > question[j + 1].usage) {
        // swap
        var temp = question[j];
        question[j] = question[j+1];
        question[j + 1] = temp;
      }
     }
    }
    return question;
  }
function bubble2(question) {
    var len = question.length;
  
    for (var i = 0; i < len ; i++) {
      for(var j = 0 ; j < len - i - 1; j++){ // this was missing
      if (question[j].usage < question[j + 1].usage) {
        // swap
        var temp = question[j];
        question[j] = question[j+1];
        question[j + 1] = temp;
      }
     }
    }
    return question;
  }



