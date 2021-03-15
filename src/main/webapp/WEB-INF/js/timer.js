var time
var hour = 0;
var second = 0;
var min = 0;
function startTimer(count) { 
	
	hour = Math.floor(count/3600)
	count = count- hour*3600;
	min = Math.floor(count/60);
	second = count- min*60;

	time = hour + ":" + min +":" + second;

	
	
	document.getElementById("timer").innerHTML ="Time Left: "+ time;

	var x = setInterval(function() {
		
		  
		  if(second ==0 && min !=0){
			  min--;
			  second =59;
		  }
		  second --;
		  if(min ==0 && hour!=0){
			  hour--;
			  min=59;
			  second=59;
		  }
		  time = hour + ":" + min +":" + second;
		  document.getElementById("timer").innerHTML ="Time Left: "+ time;
		  
		  var duration = (hour*3600 + min*60 + second);
		  document.getElementById('duration').value= duration;
		  
		  if(hour ==0 && min==0 && second==0){
			  document.getElementById("SUBMIT").click();
			  alert("Exam is finish");
			  
			  clearInterval(x);
		  }
		
	}, 1000);
}