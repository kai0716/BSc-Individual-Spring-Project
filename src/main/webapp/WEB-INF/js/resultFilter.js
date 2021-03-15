var list=[];
var length =0;

function Search(){
	var search_message = $("#search").val();
	var message = search_message.replace(/\s/g,'');
	length= search_message.length;
	console.log(length);

	<c:forEach var="student" items="${Student}"> 
		var studentName = "${student.firstName}" +"${student.lastName}"; 
		if(!
				studentName.toLowerCase().includes(message.toLowerCase())){
			
			var student = new Object();
			student.id="${student.userId}"
			student.email = "${student.email}";
			student.firstName = "${student.firstName}";
			student.lastName = "${student.lastName}";
			student.examListSize = "${student.examList.size()}";
			student.lastName = "${student.lastName}";
			showTable(student);
		}
	console.log(!
	studentName.toLowerCase().includes(message.toLowerCase()));
	</c:forEach>
}

function showTable(student){
	var tableID = 's' +student.id;
	list.push($("#"+tableID));	
	$('#'+tableID).empty();

}
$(document).ready(function(){

	var search = $("#search");
	search.keyup(function(){
		if(search.val()=="" || (search.val()).length < length){
			location.reload();
		}
	})
})