<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>移动血糖信息管理系统</title>
<link rel="shortcut icon"  href="favicon.ico"/>
<link href="${pageContext.request.contextPath}/css/layout1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	var id = 0;
	$(document).ready(function(){
		//$("#test").click(function(){
		//	checkload();
		//});
		id = window.setInterval("checkload()",3000);
	});
	
	function checkload(){
		$.ajax({
	      	type: "GET",
	      	url:"${pageContext.request.contextPath}/upload/getUploadState.do",
	      	data:"",
	      	dataType:"text",
	      	success:function(data) {
				if(data == 'success'){
					window.clearInterval(id);
					alert("数据上传完毕！");
					window.location = "${pageContext.request.contextPath}/show/showindex.do";
					
				}else{
					//alert('加载中!');
				}
	      	},
	        error:function(XMLHttpRequest, textStatus, errorThrown) {
	        	
	        	alert(XMLHttpRequest+"\n"+textStatus+"\n"+errorThrown);
	        }
		});  
	}
</script>
</head>
<body>
<div class="rk">
	<input type="button" id="test"  value="测 试"/>
	<c:choose>
		<c:when test="${status eq 'success' }">
			数据上传中,请稍候......
		</c:when>
		<c:otherwise>
			文件上传失败 请稍后再试！
		</c:otherwise>
	</c:choose>
</div>
<div class="bottom_color"></div>
</body>
</html>

