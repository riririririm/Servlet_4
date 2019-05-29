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
	<div class="container">
		<h2>로그인 페이지</h2>
		<form action="./memberLogin" method="post">
			<div class="form-group">
				<label for="id">Id:</label> <input type="text" class="form-control"
					id="id" placeholder="Enter Id" name="id" value="${request.session.id}">
			</div>
			<div class="form-group">
				<label for="pw">Password:</label> <input type="password"
					class="form-control" id="pw" placeholder="Enter password" name="pw">
			</div>
			<div class="checkbox">
				<label><input type="checkbox" name="remember" value="check">
					Remember me</label>
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div>
</body>
</html>