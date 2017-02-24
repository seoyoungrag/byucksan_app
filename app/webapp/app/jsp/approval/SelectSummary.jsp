<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectSummary.jsp 
 *  Description : 문서요약
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
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.summary'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	if (opener != null && opener.getSummary != null) {
		var summary = opener.getSummary();
		document.getElementById("summary").value = summary;
	}		
}

function closeSummary() {
	window.close();
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.select.summary'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="table_grow">
					<tr bgcolor="#ffffff">	
						<td width="18%" class="tb_tit"><spring:message code='approval.title.summary'/></td>
						<td width="82%" class="tb_left_bg">
							<textarea id="summary" name="summary" style=" width:100%; height:480px; overflow:auto;" readonly></textarea> 
						</td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="closeSummary();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>