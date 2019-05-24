<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
</head>
<body>
	<c:import url="../temp/header.jsp"/>
	<div class="container">
		<h1>${board} list</h1>
		<table class="table table-hover">
			<tr>
				<td>NUM</td>
				<td>TITLE</td>
				<td>WRITER</td>
				<td>DATE</td>
				<td>HIT</td>
			</tr>

			<c:forEach items="${list}" var="dto">
				<tr>
					<td>${dto.num}</td>
					<td>
						<c:catch>
						<c:forEach begin="1" end="${dto.depth}" varStatus="i">
						--
						<c:if test="${i.last}">&gt;</c:if>
						</c:forEach>
						</c:catch>
						<a href="./${board}Select?num=${dto.num}">${dto.title}</a>
					</td>
					<td>${dto.writer}</td>
					<td>${dto.reg_date}</td>
					<td>${dto.hit}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="container">
		<ul class="pager">
			<c:if test="${pager.curBlock gt 1}">
				<li class="previous"><a href="./${board}List?curPage=${pager.startNum-1}&kind=${pager.search.kind}&search=${pager.search.search}">Previous</a></li>
			</c:if>
			
			<li>
				 <ul class="pagination">
				 	<c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i" step="1">
				 		<li><a href="./${board}List?curPage=${i}&kind=${pager.search.kind}&search=${pager.search.search}">${i}</a></li>
				 	</c:forEach>
 				 </ul>
			</li>
			
			<c:if test="${pager.curBlock lt pager.totalBlock}">
				<li class="next"><a href="./${board}List?curPage=${pager.lastNum+1}&kind=${pager.search.kind}&search=${pager.search.search}">Next</a></li>
			</c:if>
		</ul>
		<!-- 주소창에 적힐 주소 -->
	<a href="./${board}Write">Go Write</a>
	</div>
	
</body>
</html>