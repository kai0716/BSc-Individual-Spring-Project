$(document).ready(function() {		
$("#addExam_validation").validate({
	errorClass:"error",
			rules : {
				examName : {
					required : true,
				},
				startTime : {
					required : true,
				},
			},
		})
})		