<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<input class="skipThese" type="hidden" name="pageCount" id="pageCount"
		value="${pageModel.pageCount}" />
	<input class="skipThese" type="hidden" name="currentPage"
		id="currentPage" value="${pageModel.pageNo}" />
	<tr>
		<td>
			<div>
				<span width="33%" class="STYLE22" align="left">&nbsp;&nbsp;&nbsp;&nbsp;
					共 <strong>${pageModel.pageCount}</strong> 页, <strong>
						${pageModel.rowsCount}</strong>条记录</span>
			</div>
		</td>
		<td width="67%">
			<table width="auto" border="0" align="right"
				cellpadding="0" cellspacing="0">
				<tr>
					<c:if test="${pageModel.pageNo==1 }">
						<td><div align="center" class="page_shouye">
								<a href="javascript:">首页</a>
							</div>
						</td>
						<td><div align="center" class="page_next">
								<a href="javascript:"> 前一页</a>
							</div>
						</td>
					</c:if>
					<c:if test="${pageModel.pageNo > 1 }">
						<td><div align="center" class="page_shouye">
								<a href="javascript:first();">首页</a>
							</div>
						</td>
						<td><div align="center" class="page_next">
								<a href="javascript:previous();">前一页</a>
							</div>
						</td>
					</c:if>
					<td><div align="center" class="page_shuzi">
							<c:set var="viewCount" value="5" />
							<fmt:parseNumber var="halfCount" integerOnly="true"
								value="${viewCount/2}" />
							<c:if test="${pageModel.pageCount <= viewCount}">
								<c:set var="istart" value="1" />
								<c:set var="istop" value="${pageModel.pageCount}" />
							</c:if>
							<c:if test="${pageModel.pageCount > viewCount}">
								<c:set var="istart" value="${pageModel.pageNo - halfCount}" />
								<c:set var="istop" value="${pageModel.pageNo + halfCount}" />
								<c:if test="${istart <= 1}">
									<c:set var="istart" value="1" />
									<c:set var="istop" value="${viewCount}" />
								</c:if>
								<c:if test="${istop >= pageModel.pageCount}">
									<c:set var="istop" value="${pageModel.pageCount}" />
									<c:set var="istart" value="${istop-viewCount+1 }" />
								</c:if>
							</c:if>
							<c:forEach var="iNum" begin="${istart}" end="${istop}" step="1">
								<c:if test="${pageModel.pageNo!=iNum}">
									<a title="${iNum}" href="javascript:jumpPage(${iNum});">${iNum}</a>
								</c:if>
								<c:if test="${pageModel.pageNo==iNum}">
									<a title="${iNum}" href="javascript:"
										style="color:#FF0000;border:0px;cursor:default;">${iNum}</a>
								</c:if>
							</c:forEach>
							<div style="clear:both; font-size:0; height:0; overflow:hidden"></div>
						</div>
					</td>

					<c:if test="${pageModel.pageNo < pageModel.pageCount}">
						<td><div class="page_next">
								<a href="javascript:next();">下一页</a>
							</div>
						</td>
						<td><div class="page_shouye">
								<a href="javascript:end();">尾页</a>
							</div>
						</td>
					</c:if>
					<c:if test="${pageModel.pageNo==pageModel.pageCount}">
						<td><div class="page_next">
								<a href="javascript:">下一页</a>
							</div>
						</td>
						<td><div class="page_shouye">
								<a href="javascript:">尾页</a>
							</div>
						</td>
					</c:if>
					<td class="STYLE22"><div style="margin:0px 5px">跳转至</div>
					</td>
					<td><div align="center" class="page_jump_no">
							<input class="skipThese" type="text" name="pageNo" id="pageNo"
								value="${pageModel.pageNo}"
								style="width:22px; height:16px; font-size:12px; border:solid 1px #CACACA; line-height:16px;text-indent:2px;background:#fff " />
						</div>
					</td>
					<td class="STYLE22"><div style="margin:0px 5px">页</div>
					</td>
					<td class="STYLE22"><div class="page_shouye01">
							<a href="javascript:jumpForButton();">go</a>
						</div>
					</td>
				</tr>
			</table>
			</td>
	</tr>
</table>
<script type="text/javascript">
	function first() {
		jumpPage(1);
	}

	function previous() {
		var currentPage = $("#currentPage").val();
		currentPage = parseInt(currentPage, 10);
		var jumpPageNo = currentPage - 1;
		if (jumpPageNo <= 1) {
			jumpPageNo = 1;
		}
		jumpPage(jumpPageNo);
	}
	function next() {
		var currentPage = $("#currentPage").val();
		currentPage = parseInt(currentPage, 10);
		var jumpPageNo = currentPage + 1;
		var pageCount = $("#pageCount").val();
		if (jumpPageNo > pageCount) {
			jumpPageNo = pageCount;
		}

		jumpPage(jumpPageNo);
	}
	function end() {
		var pageCount = $("#pageCount").val();
		jumpPage(pageCount);
	}

	function jumpForButton() {
		var jumpPageNo = $("#pageNo").val();
		if ($.isNumeric(jumpPageNo)) {
			jumpPageNo = parseInt(jumpPageNo, 10);
		} else {
			$("#pageNo").val("");
			return;
		}

		if (jumpPageNo <= 1) {
			jumpPageNo = 1;
		}
		if (jumpPageNo > 1) {
			var pageCount = $("#pageCount").val();
			if ($.isNumeric(pageCount)) {
				pageCount = parseInt(pageCount, 10);
			}
			if (jumpPageNo > pageCount) {
				jumpPageNo = pageCount;
			}
		}
		jumpPage(jumpPageNo);
	}

	function resetPageNo() {
		$("#pageNo").val(1);
	}

	function jumpPage(pageNo) {
		var formId = $("#pageNo").closest("form").attr("id");
		$('input').trigger('submitForm');
		$("#pageNo").val(pageNo);
		$("#" + formId).submit();
	}
</script>
