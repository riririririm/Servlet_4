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
$(function() {
	$("#btn").click(function() {
		$.get("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20190528", function(data) {
			data.boxOfficeResult.dailyBoxOfficeList.forEach(function(d) {
				var movieNm = d.movieNm;
				var openDt = d.openDt;
				var result = "<div><h1>"+movieNm+"</h1><h2>"+openDt+"</h2></div>";
				$("#result").append(result);
			});
			
		});
	});
});
</script>
</head>
<body>
	<button id="btn">click</button>
	<div id="result">
	
	</div>
</body>
</html>