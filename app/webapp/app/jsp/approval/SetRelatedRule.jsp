<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SetRelatedRule.jsp 
 *  Description : 관련규정 선택 
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
String wikicontent = CommonUtil.nullTrim((String)request.getParameter("wikicontent"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.relatedrule'/></title>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	if (parent != null && parent.setWikiContents != null) {
//		parent.setWikiContents("<%=EscapeUtil.escapeJavaScript(wikicontent)%>");
		parent.setWikiContents("6|27|004|업무분장세칙");
	}
}
</script>
</head>
<body>
</body>
</html>