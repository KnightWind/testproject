<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/reset.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/style/rightbox.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/style/list.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>	
<script type="text/javascript">
 
	 
</script>
</head>
<body>
<form id="query" name="query" action="/manage/infolist/${moudulId}" method="post">
	<div class="main_content">
			<input type="hidden"  name="moudulId" value="${moudulId}"/>
			<input type="hidden"  name="id" value=""/>
		<div class="m_top1">
			<input name="button_01" class="button_01" onclick="toEditInfo();" type="button" value="添加信息"
				onmouseover="this.className='Btn_Hover_01'"
				onmouseout="this.className='Btn_01'" />
		</div>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="table_box">
			<tr class="table003" height="38">
				<td width="50%" height="38" bgcolor="d3eaef" class="STYLE10"><div
						align="center">
						<span><b>姓名</b></span>
					</div>
				</td>
				<td width="20%" height="38" bgcolor="d3eaef" class="STYLE10"><div
						align="center">
						<span><b>住址</b></span>
					</div>
				</td>
				<td width="10%" height="38" bgcolor="d3eaef" class="STYLE10"><div
						align="center">
						<span><b>生日</b></span>
					</div>
				</td>
				<td width="20%" height="38" bgcolor="d3eaef" class="STYLE10"><div
						align="center">
						<span><b>性别</b></span>
					</div>
				</td>
			</tr>
			<c:if test="${fn:length(pageModel.datas)<=0}">
				<tr class="table001" height="32"  >
			            <td height="32" colspan="11" align="center">无数据</td>
			     </tr>
			</c:if>
			<c:forEach var="info" items="${pageModel.datas}" varStatus="status">
				<tr class="table001" height="32">
					<td height="32">
						<div align="center">
							<span>${info.title}</span>
						</div>
					</td>
					<td height="32">
						<div align="center">
							<span><fmt:formatDate value="${info.createTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></span>
						</div>
					</td>
					<td height="32">
					<div align="center">
							<span id="showIndexInfo${info.id }">
								<c:choose>
									<c:when test="${info.showIndex eq 0 }">
										 不显示
									</c:when>
									<c:otherwise>
										显示
									</c:otherwise>
								</c:choose>
							</span>
						</div>
					</td>
					<td height="32">
						<div align="center">
							 
						</div>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td class="table_bottom" height="38" colspan="6"> 
					 <jsp:include page="/common/page_info.jsp" />
				</td>
			</tr>
		</table>
	</div>
	</form>
</body>
</html>

