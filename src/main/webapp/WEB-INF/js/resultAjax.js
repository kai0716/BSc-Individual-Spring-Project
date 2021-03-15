function AjaxAdd(module){
	console.log(module);
		$.ajax({
            async: false,
            type: "POST",
            url: "/teacher/result"+module,

            success: function(response) {
            	var index=0;
            	$("#tbody").empty();
            	for(var i=0; i<response.length; i++){
            		var exam =response[i];
            		index++;
            		
            		if(exam.mark !="NaN"){
            			showTable(exam.exam, exam.mark, index, exam.id, exam.studentNumber);
            		}
            		else{
               			showTable(exam.exam, "0", index, exam.id);
            		}
            	}
            } 
	});
}
function Release(num, id){
	console.log(num+"A");

	$.ajax({
        async: false,
        type: "POST",
        url: "/teacher/release",
        data:{num: num, examId:id},

        success: function(response) {
        	if(response =="Released"){
        		alert("Released");
        	}
        	if(response =="Failed"){
        		alert("Release Failed");
        	}
        } 
});
}
function showTable(examName, examMark,index,id, studentNumber){
	var add_td = document.getElementById("tbody");
	   $( '<tr>'+	
				'<td>'+index+'</td>'+
				'<td>'+examName+'</td>'+
				'<td>'+examMark+'%'+'</td>'+
				'<td>'+studentNumber+'</td>'+
				'<td><button type="button" id="button" class="btn btn-default" onclick="Release(1,'+id+')">Release</button></td>'+
		  '</tr>').appendTo(add_td);
}