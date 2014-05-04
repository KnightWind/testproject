<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>index</title>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
	<script type="text/javascript">
	 	$(document).ready(function(){
	 		$("#fileselect").change(function(){
				$("#showfile").val($(this).val());
			});
	 	});
		 	
		function importWaiting(){
			$("#importholder").show();
			$("#uploadbody").hide();
			setInterval(function(){
				var text = $("#moredot").text();
				if(text){
					$("#moredot").text("");
				}else{
					$("#moredot").text(".....");
				}
			},500)
		}
	</script>
</head>

<body style="padding: 0px;">
<div class="import-dialog" style="height: 1068px;width: 960px;margin-left: 300px;">
	<form id="uploadForm" action="${pageContext.request.contextPath}/upload/uploadfile.do" method="post" enctype="multipart/form-data">
		<div id="uploadbody" class="upload-contact form-body" style="margin: 100px auto 100px;">
			<div class="form-item" style="height:35px;">
				<label class="title">上传文件</label>
				<div class="widget">
					<input type="text" readonly="readonly" id="showfile"   class="input-text" />
					<a id="testselectbtn" class="input-gray" style="position:relative;width:55px;height:28px;text-align:center;">选择文件
						<input type="file" name="excelfile" id="fileselect"  style="cursor: pointer;position:absolute;left:0;top:0;width:100%;height:100%;z-index:999;opacity:0;filter:alpha(opacity=0);"/>
					</a>
				</div>
			</div>
		</div>
		<div class="form-buttons import-dialog-buttons">
			<input type="submit" class="button input-gray" value="上传"/>
		</div>
	</form>
</div>
</body>
</html>