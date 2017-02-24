<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Properties"%>
<%@ page import="org.jconfig.Category"%>
<%
/** 
 *  Class Name  : UpdateFile.jsp 
 *  Description : 파일업로드 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	// 버튼명
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='appcom.title.stor.download'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
}

function listFileInfo() {
	var docId = $.trim($("#docId").val());
	$("#docId").val(docId);
	if (docId == "") {
		alert("<spring:message code='appcom.msg.notexist.docid'/>");
		return false;
	}
	document.selectform.submit();
} 
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='appcom.title.stor.download'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="listFileInfo();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<form id="selectform" name="selectform" method="post" action="<%=webUri%>/app/approval/admin/listFileInfo.do" onsubmit="return false;">
					<table cellpadding="2" cellspacing="1" width="100%" class="table">
				 		<tr bgcolor="#ffffff">
							<td class="ltb_head" width="20%" style="height: 28px;"><spring:message code='appcom.title.docid'/></td>
							<td class="tb_left_bg" width="80%" style="height: 28px;">
								<input type="text" id="docId" name="docId" class="input"/ style="width:100%"/>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>