<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	window.onload = function() {
		/* $("#btn").click(function() {
			alert($("#num").val());
		}); */
		
		var num=document.getElementById("num");
		var btn = document.getElementById("btn");
		//btn.onclick = function(){};
		
		btn.addEventListener("click", function() {
			//alert(num.value);
			
			// ../notice/noticeSelect
			var xhttp;
			if(window.XMLHttpRequest){
				xhttp = new XMLHttpRequest();
			}else{
				xhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xhttp.open("GET","../notice/noticeSelect?num="+num.value,true);//true : 비동기
			xhttp.send();
			
			xhttp.onreadystatechange = function() {
				if(this.readyState==4 && this.status==200){
					alert(this.responseText);
				}
			}
		});
		
	}
</script>
</head>
<body>
	<input type="text" id="num">
	<button id="btn">click</button>
</body>
</html>