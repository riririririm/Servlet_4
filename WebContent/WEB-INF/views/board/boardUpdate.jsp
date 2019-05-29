<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
<script type="text/javascript" src="../se2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
var oEditors = [];
	$(function() {
		
	      nhn.husky.EZCreator.createInIFrame({
	          oAppRef: oEditors,
	          elPlaceHolder: "contents",
	          //SmartEditor2Skin.html 파일이 존재하는 경로
	          sSkinURI: "/Servlet_4/se2/SmartEditor2Skin.html",  
	          htParams : {
	              // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	              bUseToolbar : true,             
	              // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	              bUseVerticalResizer : true,     
	              // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	              bUseModeChanger : true,         
	              fOnBeforeUnload : function(){
	                   
	              }
	          }, 
	          fOnAppLoad : function(){
	              //기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
	              oEditors.getById["contents"].exec("PASTE_HTML", ["기존 DB에 저장된 내용을 에디터에 적용할 문구"]);
	          },
	          fCreator: "createSEditor2"
	      });
	      
	      //저장버튼 클릭시 form 전송
	      $("#save").click(function(){
	          oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
	          $("#frm").submit();
	      });    
	
		
	
	

		
		
		/////////////////////
		var count=${upload.size()};
		var d1=0;
		
		//DB 삭제
		$(".fileDelete").click(function() {
			//1. XMLHttpRequest 생성
			var id = $(this).attr("id");
			var xhttp;
			if(window.XMLHttpRequest){
				xhttp= new XMLHttpRequest();
			}else {
				xhttp= new ActiveXObject("Microsoft.XMLHTTP");
			}
			//2. 연결정보 작성
			xhttp.open("GET", "../file/fileDelete?pnum="+id, true);
			//3. 전송
			xhttp.send();
			//4. 결과처리
			xhttp.onreadystatechange=function(){
				if(this.readyState==4&&this.status==200){
					if(this.responseText.trim()=='1'){
						$("#"+id).prev().remove();
						$("#"+id).remove();
						count--;
					}else {
						alert("삭제 실패");
					}
				}
			}
		});
		
		//
		$("#add").click(function(e) {
			d1++;
			if(count<5){
				$("#addFile").append('<div id="'+d1+'"><input type="file" class="form-control" id="" name="f'+d1+'"><span title="'+d1+'" class="del">X</span></div>')
				count++;
				
			}else {
				alert('5개만 가능');
			}
		
		});
		
		///////////////////
		$("#addFile").on("click", ".del", function() {
			//$(this).prev().remove();
			//$(this).remove();
			var v = $(this).attr('title');
			$('#'+v).remove();
			count--;
			
		});
	});
</script>
<style type="text/css">
	.del {
		color: red;
		cursor: pointer;
	}
</style>
</head>
<body>

<c:import url="../temp/header.jsp" />
	<div class="container">
		<h1>${board} Update Page</h1>
		<form id="frm" action="./${board}Update" method="post" enctype="multipart/form-data">
		    <input type="hidden" name="num" value="${dto.num}">
		    <div class="form-group">
		      <label for="title">Title:</label>
		      <input type="text" class="form-control" id="title" value="${dto.title}" name="title">
		    </div>
		    <div class="form-group">
		      <label for="writer">Writer:</label>
		      <input type="text" class="form-control" id="writer" readonly="readonly" value="${dto.writer}" name="writer">
		    </div>
		    <div class="form-group">
			  <label for="contents">Contents:</label>
			  <textarea class="form-control" rows="20" id="contents" name="contents">${dto.contents}</textarea>
			</div>
			
			<div class="form-group" id="addFile">
		      <label for="file">File:</label>
		      
		      <c:forEach items="${upload}" var="up">
		      <span>${up.oname}</span><span id="${up.pnum}" class="fileDelete">X</span>
		     
		      </c:forEach>
		    </div>
		    
		    <div class="form-group">
		    	<input type="button" value="Add" id="add" class="btn btn-primary">
		    </div>
		    
		    <input type="button" id="save" class="btn btn-danger" value="Update">
		  </form>
	
	
	</div>


</body>
</html>