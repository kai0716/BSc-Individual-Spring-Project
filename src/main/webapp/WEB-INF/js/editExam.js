function editAjaxPost(id) {
	
	$('#setExamID').val(id);
	
	var send = false;
	$.ajax({
		async : false,
		type : "GET",
		url : "/teacher/Exam/ModifyExamQuestion/Edit" + id,

		success : function(result) {
			
			console.log(result);
			$("#examName").val(result.examName).css('font-weight', 'bold');
			$("#examDuration").val(result.examDuration).css('font-weight', 'bold');
			
		}
		

	})
}