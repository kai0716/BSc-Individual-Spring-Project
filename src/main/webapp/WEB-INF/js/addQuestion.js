var choiceInput = 0;
var choiceInputId = choiceInput + "A";
var vc = 0;
var choiceLabelId = vc+"a";

var answerInput = 0;
var answerInputId = answerInput +"B";
var va = 0;
var answerLabelId = va +"b";
$(document).ready(function() {
	
	$("#addQuestion_validation").validate({
		errorClass: "error",
		rules : {
			question : {
				required : true,
			},
			choice : {
				required : true,
			},
			answer : {
				required : true,
			}
		},

// Use AJAX to pass data .
    
    	submitHandler:function(form){
        var question = document.getElementById("question").value;

        var newString = document.getElementById("choice").value;
        
        var choice2 = document.getElementById("choice2").value;

        var answer = document.getElementById("answer").value;
        
        var module = document.getElementById("module").value;
        
        var mark = document.getElementById("mark").value;
        
        newString = newString + ",,," + choice2;
        
        // if use 0 for the first added input field, you cannot delete that input field, because you cannot use 0 to delete 1. 
        for (var i = 1; i < choiceInput+1; i++) {

            newID = i+'A';
            newValue = document.getElementById(newID).value;

            newString = newString + ",,," + newValue;
        }
        
        for (var i = 1; i < answerInput+1; i++) {

            newID = i+'B';
            newValue = document.getElementById(newID).value;

            answer = answer + ",,," + newValue;
        }
        
        console.log(newString +"adsf");
	        $.ajax({
	            async: false,
	            type: "POST",
	            url: "/teacher/question",
	            data: "&choice=" + newString + "&question=" + question + "&answer=" + answer + "&module=" + module + "&mark=" + mark,
	
	            success: function(response) {
	                if (response == "Success") {
	                    alert("Add Success");
	                    form.submit();
	                }
	                if(response == "QuestionExist"){
	                    alert("This question is exist");
	               	 
	                }
	
	
	            }
	        });
    	}
    })
 // function for user to add input field when they select modules
    $("#addRows").click(function() {
    	// allow create 4 more input field
    	if(choiceInput<4){
	        //give an id to each input field and label tag
	        // lable id and input id cannot have same value, Id must be unique, so I use 1A,2A,3A for input ID, and use 1a,2a,3a for label ID.   
	    	choiceInput = choiceInput + 1; 
	    	choiceInputId = choiceInput +"A"; // inputId of each input will be 1A,2A,3A... 
	        vc = vc + 1; //1,2,3 ....
	        choiceLabelId = vc + "a"; //labelId of each label  will be 1a,2a,3a ..
	        
	        choiceId = choiceInput+2;
	        
	        var addDiv = document.getElementById("addChoice");
	        console.log(choiceInputId);
	        $('<div class="form-group"><label path="choice" style="font-size:20px;" id="' + choiceLabelId +'">Choice '+choiceId+'</label>' +
	        		'<input class="form-control" path="choice" id ="' + choiceInputId +'"/></div>').appendTo(addDiv);
    	}
    })

    $("#addAnswerRows").click(function() {
    	
    	if(answerInput<2){
        //give an id to each input field and label tag
        // lable id and input id cannot have same value, so I use 1,2,3 for input ID, and use 1a,2a,3a for label ID.   
    	answerInput = answerInput + 1; 
    	answerInputId = answerInput +"B"; // inputId of each input will be 1B,2B,3B... 
        va = va + 1; //1,2,3 ....
        answerLabelId = va + "b"; //labelId of each label  will be 1b,2b,3b ..
        
        answerId = answerInput+1;
        
        var addDiv = document.getElementById("addAnswer");
        console.log(answerInputId);
        $('<div class="form-group"><label style="font-size:20px;" path="choice" id="' + answerLabelId +'">Answer '+answerId+'</label>' +
        		'<input class="form-control" path="choice" id ="' + answerInputId +'"/></div>').appendTo(addDiv);
    	}
    })

//    <div class="form-group"><label path="choice">Choice</label><input class="form-control" path="choice" id ="choice"/></div>

});

//function for user to delete module input field.
$(document).ready(function() {

    $('#removeRows').click(function() {
        var element = document.getElementById(choiceInputId	);
        var element1 = document.getElementById(choiceLabelId);
        element.parentNode.removeChild(element);
        element1.parentNode.removeChild(element1);
        choiceInput = choiceInput - 1;
        choiceInputId = choiceInput + "A";
        vc = vc - 1; 
        choiceLabelId = vc + "a";

    })
    
    $('#removeAnswerRows').click(function() {
        var element = document.getElementById(answerInputId);
        var element1 = document.getElementById(answerLabelId);
        element.parentNode.removeChild(element);
        element1.parentNode.removeChild(element1);
        answerInput = answerInput - 1;
        answerInputId = answerInput +"B";
        va = va - 1;
        answerLabelId = va + "b";

    })
});