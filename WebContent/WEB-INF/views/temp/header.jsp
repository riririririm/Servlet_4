<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="<%=application.getContextPath()%>/index.do">Home</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="<%=application.getContextPath()%>/notice/noticeList">Notice</a></li>
      <li class="dropdown"><a href="${pageContext.request.contextPath}/qna/qnaList">QnA<span class="caret"></span></a></li>
      <li><a href="#">Page 2</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
    	<c:choose>
			<c:when test="${not empty sessionScope.member}"> <!-- if -->
				<li><a href="<%=application.getContextPath()%>/member/memberPage"><span class="glyphicon glyphicon-user"></span> MyPage</a></li>
      			<li><a href="<%=application.getContextPath()%>/member/memberLogout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
			</c:when>
			<c:otherwise><!-- else -->
				<li><a href="<%=application.getContextPath()%>/member/memberCheck"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
      			<li><a href="<%=application.getContextPath()%>/member/memberLogin"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
			</c:otherwise>    	
    	</c:choose>
      
    </ul>
  </div>
</nav>


<div class="container">
  <div class="jumbotron">
    <h1>Bootstrap Tutorial</h1>      
    <p>Bootstrap is the most popular HTML, CSS, and JS framework for developing responsive, mobile-first projects on the web.</p>
  </div>
   
</div>
