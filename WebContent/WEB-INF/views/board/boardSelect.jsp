<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
<script type="text/javascript">
	$(function() {
		getList();
		
		var curPage=1;
		
		$("#more").click(function() {
			curPage++;
			getList(curPage);
		});
		
		$("#updateBtn").click(function() {
			//alert($('#updateContents').val());
			//alert($("#cnum").val());
			var contents = $("#updateContents").val();
			var cnum =$("#cnum").val();
			$.post("../comments/commentsUpdate",{
				contents:contents,
				cnum:cnum
			}, function(data) {
				data = data.trim();
				if(data=="1"){
					alert("success");
					$("#c"+cnum).html(contents);
					//location.reload(); //or getList(1);
				}else {
					alert("Fail");
				}
			});
			
			
		});//end of updateBtn click
		
		$("#btn").click(function() {
			var writer = $("#writer").val();
			var contents = $("#contents").val();
			var num = '${dto.num}';
			$.post("../comments/commentsWrite",{
				writer:writer,
				contents:contents,
				num:num
			}, function(data) {
				data = data.trim();
				if(data=="1"){
					alert("success");
					location.reload();
				}else {
					alert("Fail");
				}
			});
			
			
		});
	
		
		//
		function getList(count) {
			$.get("../comments/commentsList?num=${dto.num}&curPage="+count, function(data) {
				data = data.trim();
				if(count==1){
					$("#commentsList").html(data);
				}else{
					$("#commentsList").append(data);
				}
			});
			
			
		}

			
		$("#commentsList").on("click", ".del", function() { //list.jsp의 버튼에 이벤트 위임
			var cnum = $(this).attr("id");
			var check = confirm("delete??");
			if(check){
				$.get("../comments/commentsDelete?cnum="+cnum,function(data){//data = respnseText의 값
					data = data.trim();
					if(data=="1"){
						alert("성공");
						location.reload();
					}else {
						alert("실패");
					}
				});	
			}
		});
		
		$("#commentsList").on("click",".update", function() {
			var id = $(this).attr("title");
			var con = $("#c"+id).html();
			$("#updateContents").val(con);
			$("#cnum").val(id);
		});
	
	});
</script>
</head>
<body>
<c:import url="../temp/header.jsp" />
	
	<div class="container">
		<h1>${board } Select Page</h1>
		<h1>Title : ${dto.title} </h1>
		<h1>Contetns : </h1>
		<div> ${dto.contents}</div>
		<h1>Writer : ${dto.writer}</h1>
		<h1>Param    : ${param.num ge dto.num}</h1>
		<h1>Writer : ${dto.writer ne 'test'}</h1>
		<c:catch>
		<c:forEach items="${upload}" var="up">
		<h1>Upload : <a href="../upload/${up.fname}">${up.oname}</a></h1>
		</c:forEach>
		</c:catch>
	
	</div>
	<c:if test="${board ne 'notice'}">
		<div class="container">
			<!-- 댓글입력폼 -->
			<div class="row">
				<div class="form-group">
		      <label for="writer">Writer:</label>
		      <input type="text" class="form-control" id="writer" name="writer">
		    </div>
		    <div class="form-group">
			  <label for="contents">Contents:</label>
			  <textarea class="form-control" rows="10" id="contents" name="contents"></textarea>
			   <button class="btn btn-danger" id="btn">Write</button>
			</div>
			</div>
			
			<!-- 댓글리스트 -->
			<div class="row">
				<table class="table table-bordered" id="commentsList">
					
				</table>
				<button id="more">더 보기</button>
			</div>
		
		</div>
	</c:if>
	
	<div class="container">
	  <!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog">
	    
	      <!-- Modal content-->
	      <div class="modal-content">
	        <div class="modal-header">
	          <h4 class="modal-title">${member.id}</h4>
	        </div>
	        <div class="modal-body">
		         <div class="form-group">
				  <label for="contents">Contents:</label>
				  <textarea class="form-control" rows="10" id="updateContents" name="contents"></textarea>
				  <input type="hidden" id="cnum">
				</div>
	        </div>
	        <div class="modal-footer">
			   <button class="btn btn-danger" id="updateBtn" data-dismiss="modal">Update</button>
	          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        </div>
	      </div>
	      
	    </div>
	  </div>
	</div>
	
	<a href="./${board}Update?num=${dto.num}">GO Update</a>
</body>
</html>






