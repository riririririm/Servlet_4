<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../temp/bootstrap.jsp"/>
<style type="text/css">
	.main{
		margin-bottom: 100px;
	}
</style>
</head>
<body>
	<jsp:include page="../temp/header.jsp"/>
	<div class="container main">
		<h1>회원정보 수정페이지</h1>
		<form action="./memberUpdate" id="frm" method="post">
			<div class="form-group">
				<label for="id">Id:</label> <input type="text" class="form-control"
					id="id" placeholder="Enter Id" name="id" value="${sessionScope.member.id }" readonly="readonly"> 
			</div>
			<div class="form-group">
				<label for="pwd">Password:</label> <input type="password"
					class="form-control" id="pwd1" placeholder="Enter password">
			</div>
			<div class="form-group">
				<label for="pwd">Password:</label> <input type="password"
					class="form-control" id="pwd2" placeholder="Enter password"
					name="pw">
			</div>
			<div class="form-group">
				<label for="name">Name:</label> <input type="text"
					class="form-control" id="name" placeholder="Enter Name" name="name" value="${sessionScope.member.name }">
			</div>

			<div class="form-group">
				<label for="email">Email:</label> <input type="email"
					class="form-control" id="email" placeholder="Enter email"
					name="email" value="${sessionScope.member.email }">
			</div>
			<div class="form-group">
				<label for="phone">Phone:</label> <input type="tel"
					class="form-control" id="phone" placeholder="Enter Phone"
					name="phone" value="${sessionScope.member.phone }" >
			</div>

			<div class="form-group">
				<label for="age">Age:</label> <input type="text"
					class="form-control" id="age" placeholder="Enter Age" name="age" value="${sessionScope.member.age }">
			</div>
			
			<div class="form-group">
				<label for="phone">file:</label> <input type="file"
					class="form-control" id="file" name="f1">
			</div>

			<input type="button" class="btn btn-default" value="Join" id="btn">
		</form>
	</div>
	
	<script type="text/javascript">
  	$(function() {
  		
  		$('#pwd2').blur(function() {
			if($(this).val()==$('#pwd1').val()){
				alert('같아요');
			}else {
				alert('달라요');
				$(this).val('');
			}
		});
  		
  		$('#pwd1').blur(function() {
			$('#pwd2').val('');
		});
  		

  		/////////////////////////////////////////////
		$('#btn').click(function() {
			var r = true;
			$(".form-control").each(function() {
				if($(this).val() == ''){
					r=false;
				}
			});
			
			if(r=='1'){
				$('#frm').submit();
			}else {
				alert('모두 작성');
			}
			
		
			
		});
	});
  
  </script>
	
</body>
</html>