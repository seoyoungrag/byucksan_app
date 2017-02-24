<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ProcessRelatedRule.jsp 
 *  Description : 관련규정선택 
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
	String returnPage = request.getParameter("returnPage");
	String relatedRule = request.getParameter("relatedRule");
	
	relatedRule = "1234567890" + String.valueOf((char)2) + "2011-1-1-1" + String.valueOf((char)2) + "2011년 관련규정집 1권" + String.valueOf((char)4)
		+ "2345678901" + String.valueOf((char)2) + "2011-1-1-2" + String.valueOf((char)2) + "2011년 관련규정집 2권" + String.valueOf((char)4)
		+ "3456789012" + String.valueOf((char)2) + "2011-1-1-3" + String.valueOf((char)2) + "2011년 관련규정집 3권" + String.valueOf((char)4); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.relatedrule'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	if (opener != null && opener.setRelatedRule != null) {
		opener.setRelatedRule("<%=EscapeUtil.escapeJavaScript(relatedRule)%>");
	}
	window.close();
}
</script>
</head>
<body>
</body>
</html>