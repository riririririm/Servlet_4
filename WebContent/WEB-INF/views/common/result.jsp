<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = (String)request.getAttribute("message");
	String path = (String)request.getAttribute("path");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- -------스크립트---------- -->
<script type="text/javascript">
	alert('${requestScope.message}');
	location.href='${requestScope.path}';
</script>

</body>
</html>