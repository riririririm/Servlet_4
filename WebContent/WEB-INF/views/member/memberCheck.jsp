<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../temp/bootstrap.jsp"/>
</head>
<body>
	<jsp:include page="../temp/header.jsp"/>
	<div class="container main">
		<h2>Vertical (basic) form</h2>
		
			<div class="checkbox">
				<label><input type="checkbox" name="remember" id="checkAll">전체동의</label>
			</div>
			<div class="checkbox">
				<label><input type="checkbox" name="remember" class="check">동의a</label>
			</div>
			<div class="checkbox">
				<label><input type="checkbox" name="remember" class="check">동의b</label>
			</div>
			<div class="checkbox">
				<label><input type="checkbox" name="remember" class="check">동의c</label>
			</div>
			
			<input type="button" class="btn btn-default" value="join" id="join">
		
	</div>
	
	<jsp:include page="../temp/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
	$(function(){
		$("#checkAll").click(function() {
			var c = $(this).prop("checked");
			$(".check").prop("checked",c);
		});
		
		
		$(".check").click(function() {
			var c = true;
			$(".check").each(function() {
				if(!$(this).prop("checked")){
					c=false;
				}
			});
			$("#checkAll").prop("checked",c);
		});
		
		
		$("#join").click(function() {
			var c=$("#checkAll").prop("checked");
			if(c){
				location.href="./memberJoin";
			}else{
				alert("약관에 모두 동의해야됨");
			}
		});
		
	});
</script>
</body>
</html>