var inputId = 0;
var v = 0;
var labelId = v + "a";
$(document).ready(function() {

		$("#register_validation").validate({
			rules : {
				email : {
					required : true,
					email:true,
				},
				firstName : {
					required : true
				},
				lastName : {
					required : true
				}
			},

// Use AJAX to pass data .
		submitHandler:function(form){
        var newString = document.getElementById("module").value;

        var email = document.getElementById("email").value;

        var firstName = document.getElementById("firstName").value;

        var lastName = document.getElementById("lastName").value;

        var userType = document.getElementById("userType").value;
        
        // if use 0 for the first added input field, you cannot delete that
		// input field, because you cannot use 0 delete 1.
        for (var i = 1; i < inputId+1; i++) {

            newID = i;
            newValue = document.getElementById(newID).value;

            newString = newString + "," + newValue;
        }
        console.log(newString);
        $.ajax({
            async: false,
            type: "POST",
            url: "/admin/UserInfo",
            data: "&module=" + newString + "&email=" + email + "&firstName=" + firstName + "&lastName=" + lastName + "&userType=" + userType,

            success: function(response) {
            	if(response=="Success"){
                    alert("Add user success");
                    location.reload();
            	}
              	if(response=="Exist"){
                    alert("Email Already exist");
            	}

            }
        }) ;
		}
	});
// function for user to add input field when they select modules
    $("#addRows").click(function() {
    	
        // give an id to each input field and label tag
        // lable id and input id cannot have same value, so I use 1,2,3 for
		// input ID, and use 1a,2a,3a for label ID.
        inputId = inputId + 1; // inputId of each input will be 1,2,3...
        v = v + 1; // 1,2,3 ....
        labelId = v + "a"; // labelId of each label will be 1a,2a,3a ..
        
        
        var addDiv = document.getElementById("addInput");
        console.log(inputId);
        $('<div class="form-group"><label style="font-size:20px;" id ="' + labelId + '">Module Code</label>' +
            '<select path="module" class="form-control"  id="' + inputId + '"><c:forEach var="module" items="${ModuleCode}"><option value="${module}">${module}</option></c:forEach></select></div>').appendTo(addDiv);
    })
    
});

// function for user to delete module input field.
$(document).ready(function() {

    $('#removeRows').click(function() {
        var element = document.getElementById(inputId);
        var element1 = document.getElementById(labelId);
        element.parentNode.removeChild(element);
        element1.parentNode.removeChild(element1);
        inputId = inputId - 1;
        v = v - 1;
        labelId = v + "a";

    })
});