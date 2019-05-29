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
	
		
		var count=0;
		var d1=0;
		$("#add").click(function() {
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
		<h1>${board} Write Page</h1>
		<form id="frm" action="./${board}Write" method="post" enctype="multipart/form-data">
		    <div class="form-group">
		      <label for="title">Title:</label>
		      <input type="text" class="form-control" id="title" name="title">
		    </div>
		    <div class="form-group">
		      <label for="writer">Writer:</label>
		      <input type="text" class="form-control" id="writer" name="writer">
		    </div>
		    <div class="form-group">
			  <label for="contents">Contents:</label>
			  <textarea class="form-control" rows="20" id="contents" name="contents"></textarea>
			</div>
			
			<div class="form-group" id="addFile">
		      <label for="file">File:</label>
		      
		    </div>
		    
		    <div class="form-group">
		    	<input type="button" value="Add" id="add" class="btn btn-primary">
		    </div>
		    
		    <input type="button" id="save" value="Write" class="btn btn-danger">
		  </form>
	
	
	</div>


</body>
</html>