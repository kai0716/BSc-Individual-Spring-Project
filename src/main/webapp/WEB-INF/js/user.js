//function doAjaxPost() {
//    // get the form values
//    var email = $('#email').val();
//    var password1 = $('#password1').val();
//    var password2 = $('#password2').val();
//    var firstName = $('#firstName').val();
//    var lastName = $('#lastName').val();
//    var birthDay = $('#birthDay').val();
//    var address = $('#address').val();
//    var bic = $('#bic').val();
//    var send = false;
//    var token = $("meta[name='_csrf']").attr("content");
//    var header = $("meta[name='_csrf_header']").attr("content");
//    $.ajax({
//    		  async:false,
//	    	  type: "POST",
//	    	  url: "/check",
//	    	  data: "email="+ email +"&password1=" + password1 + "&password2=" + password2 + "&firstName=" + firstName + "&lastName=" +
//	    	  lastName + "&birthDay=" + birthDay + "&address=" + address +"&bic=" + bic,
//
//	    	  success: function(response){
//	    		  			if(response.status == "SUCCESS"){
//	    		  					$('#info').html("User has been added to the list successfully. ");
//	    
//	    		  					$('#error').hide('slow');
//	    		  					$('#info').show('slow');
//	    		  					send = true;
//	    		  					console.log(abcdssd);
//	    		  			}
//	    		  			else{
//	    		  				 errorInfo = "";
//	    		  				 for(i =0 ; i < response.result.length ; i++){
//	    		  				 errorInfo += "<br>" + (i + 1) +". " + response.result[i].code;
//	    		  				 }
//
//	    		  				$('#error').html("Please correct following errors: " +errorInfo);
//	    		  				$('#info').hide('slow');
//	    		  				$('#error').show('slow');
//	    		  			}
//	    			   },
//	
////	    	 error: function(e){
////	    		 	alert('Error: ' + e);
////	    			}
//    })
//    return send;
//}
//
//$(document).ready(function(){
//	$("#signup_jsp").validate({
//		rules:{
//			firstName: "required",
//			lastName:"required",
//			
//			email:{
//				required: true,
//				email: true
//			},
//			password1:{
//				required: true,
//				minlength:8
//			},
//			password2:{
//				required:true,
//				minlength: 8,
//				equalTo:"#password1"
//			},
//			birthDay:{
//				required:true,
//				dateFormat:true,
//				ageValidate: true,
//				dateFormat1:true
//			},
//			address:{
//				required:true,
//			},
//			bic:{
//				required: true,
//			},
//		},
//		
//		messages:{
//
//			firstName:"Please enter your firstname",
//			lastName:"Please enter your lastname",
//			
//			email:{
//				required:"Email can not be empty.",
//			},
//			password1:{
//				required:"Password can not be empty.",
//				minlength:"The password must has at least 8 characters."
//			},
//			password2:{
//				required:"Confirm password can not be empty.",
//				minlength:"The password must has at least 8 characters.",
//				equalTo:"Please enter the same password as above."
//			},
//			birthDay:{
//				required:"Birthday can not be empty.",
//				dateFormat: "Submission date should in the format MM/DD/YYYY. Valide birth year between: 1900-2100.",
//				dateFormat1:"Submission date should in the format MM/DD/YYYY. Valide birth year between: 1900-2100.",
//				ageValidate:"Cannot register under 16."
//			},
//			address:{
//				required:"Address can not be empty."
//			},
//			bic:{
//				required:"Bic can not be empty"
//			}
//		}
//	})
//})
	
function loginAjax() {
    var email = $('#email').val();
    var password = $('#password').val();
	var send = false;
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	  $.ajax({
		  async:false,
    	  type: "POST",
    	  url: "/loginValidate",
    	  data: "email="+ email +"&password=" + password,
  
    	  success: function(response){
    		  			if(response.status == "SUCCESS"){
    		  				
    		  					send = true;
    		  					
    		  			}
    		  			else{
    		  				 errorInfo = "";
    		  				 for(i =0 ; i < response.result.length ; i++){
    		  				 errorInfo += "<br>" + (i + 1) +". " + response.result[i].code;
    		  				 }
    		  				 
    		  				$('#error').html("Please correct following errors: " +errorInfo);
    		  				$('#info').hide('slow');
    		  				$('#error').show('slow');
    		  			}
    			   },
	  })
	  return send;
}

$(document).ready(function() {
	
	$("#resetPass").validate({
		errorClass: "error",
		rules : {
			email : {
				required : true,
			},
			password : {
				required : true,
				minlength: 6,
			},
			password1 : {
				required : true,
				minlength: 6,
				equalTo: "#password"
			}
		},
		
		submitHandler:function(form){
    var email = $('#email').val();
    var password = $('#password').val();
    var password1 = $('#password1').val();
    var oldPassword = $('#oldPassword').val();
	var send = false;

	  $.ajax({
		  async:false,
    	  type: "POST",
    	  url: "/Reset/Password",
    	  data: "email="+ email +"&password=" + password +"&password1=" + password1 + "&oldPassword=" + oldPassword,
  
    	  success: function(response){
    		  			if(response == "Change success"){
    		  				send = true;
    		  				window.location.href = "http://www.://localhost:8090";
    		  			}
    		  			if(response == "oldPass incorrect"){
    		  				alert("Old Password is incorrect");
    		  			}
       		  			if(response == "User not exist"){
    		  				alert("Incorrect Email");
    		  			}
       		  			form.submit;
    			   },
	  })
	  return send;
}
	})
})
