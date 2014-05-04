<%@ page session="true"%>
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
	$(document).ready(function(){
		//$(".rk").style.height = "846px";
	});
</script>
</head>
<body>
<div class="rk">
	上传数据成功！
</div>
<div class="bottom_color"></div>
</body>
</html>

