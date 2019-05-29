<%@page import="com.rim.member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	
	MemberDAO memberDAO = new MemberDAO();
	int result = memberDAO.idCheck(id);//0, 1
	
	
	
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="./memberIdCheck.jsp">
		<input type="text" name="id" id="id" value="<%=id%>">
		<button>중복확인</button>
		<%if(result==0){ %>
			<input type="button" id="btn" value="사용하기">
		<%} %>
	</form>
	
	<!-- script -->
	<script src="<%= application.getContextPath() %>/vendor/jquery/jquery.min.js"></script>
	<script type="text/javascript">
		
		<%if(result==0){%>
			var ch=true;
		<%}else {%>
			var ch=false;
		<%}%>
		
	
		$('#btn').click(function() {
			if(ch){
				var id = $('#id').val();
				opener.document.getElementById('id').value=id;
				opener.document.getElementById('idConfirm').value=1;
				self.close();
			}else {
				alert('중복확인 !');
			}
		});
		$('#id').change(function() {
			//$('#btn').remove();
			ch=false;
		});
	</script>

</body>
</html>




