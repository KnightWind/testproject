<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<base href="<%=basePath%>">
<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>主页</TITLE>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/style/zTreeStyle/zTreeStyle.css"/>
<SCRIPT type="text/javascript" src="<%=basePath%>/js/jquery-1.8.3.js"></SCRIPT>
<SCRIPT type="text/javascript" src="<%=basePath%>/js/jquery.ztree.all-3.5.min.js"></SCRIPT>
<script type="text/javascript">
var zTree;
var demoIframe;

var setting = {
	view: {
		dblClickExpand: false,
		showLine: true,
		selectedMulti: false
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: ""
		}
	},
	callback: {
		 
	}
};

var zNodes =[
	{ id:1, pId:0, name:"用户", url:"${pageContext.request.contextPath}/show/userlist.do", target:"mainFrame"},
	{ id:2, pId:0, name:"产品", url:"manage/infolist/21", target:"mainFrame"},
	{ id:3, pId:0, name:"辅食系列", url:"/manage/photolist/31", target:"mainFrame"},
];


$(document).ready(function(){
	var t = $("#tree");
	t = $.fn.zTree.init(t, setting, zNodes);
	demoIframe = $("#mainFrame");
	demoIframe.bind("load", loadReady);
	var zTree = $.fn.zTree.getZTreeObj("tree");
	//zTree.selectNode(zTree.getNodeByParam("id", 115));
});

function loadReady() {
	var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
	htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
	maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
	h = demoIframe.height() >= maxH ? minH:maxH ;
	if (h < 530) h = 530;
	demoIframe.height(h);
	$(".main_left").height(h);
}

</script>
</HEAD>
<body style="min-width:1002px;">
<%--<jsp:include page="header.jsp" />--%>
<div class="main_left" style="float:left; height: 720px;width: 225px;" >
  	<ul id="tree" class="ztree" style="width:220px; overflow:auto;"></ul>
</div> 
<div class="main_right" style="float:left; width: 1060px;">
	<iframe frameborder="0" width="100%" height="720px" id="mainFrame" name="mainFrame" scrolling="no" src="${pageContext.request.contextPath}/show/userlist.do"></iframe>
</div>
 <div id="copy">
<span class="copy_text" ></span>
</div>
</body>
</HTML>


