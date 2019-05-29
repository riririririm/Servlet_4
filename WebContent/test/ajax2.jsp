<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script type="text/javascript">
/* 	window.onload = function() { //이게 자바스크립트. $붙은게 jquery
		var id = document.getElementById("id");
		id.addEventListener("blur", function() {
			alert(id.value);
		});
	} */
	$(function() {
		$("#id").blur(function() {
			//alert($(this).val());
			
			//1. xhttprequest 생성
			var xhttp;
			if(window.XMLHttpRequest){
				xhttp = new XMLHttpRequest();
			}else{
				xhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			//2. 요청정보 기록
			//xhttp.open("GET", "../member/idCheck?id="+$(this).val()); // 비동기, 동기 안써주면 기본이 true
			xhttp.open("POST", "../member/idCheck?id="+$(this).val());
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			
			//3. 전송
			//xhttp.send();
			xhttp.send("id="+$("#id").val());
			
			//4. response 처리
			xhttp.onreadystatechange = function(){
				if(this.readyState==4 && this.status==200){
					//alert(this.responseText.trim()=='1');
					if(this.responseText.trim()=='1'){
						$("#result").html("사용가능한 ID");
						$("#result").css("color","blue");
					}else{
						$("#result").html("사용불가능");
						$("#result").css("color","red");
						$("#id").val("").focus();
						
					}				
				}
			}
			
		});
	});
</script>

</head>
<body>
	<input type="text" id="id">
	<div id = "result"></div>
</body>
</html>