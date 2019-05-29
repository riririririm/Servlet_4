<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../temp/bootstrap.jsp" />
</head>
<body>
	<jsp:include page="../temp/header.jsp" />
	<div class="container">
		<h1>Mypage</h1>
		<h2>Id:${sessionScope.member.id }</h2>
		<h2>Name : ${sessionScope.member.name }</h2>
		<h2>Phone: ${sessionScope.member.phone }</h2>
		<h2>Email: ${sessionScope.member.email }</h2>
		<h2>Age : ${sessionScope.member.age }</h2>

		<div>
			<a href="./memberUpdate?id=${sessionScope.member.id }" class="btn btn-primary">update</a> 
			<a href="./memberDelete?id=${sessionScope.member.id }" class="btn btn-primary">delete</a>
		</div>
	</div>

</body>
</html>