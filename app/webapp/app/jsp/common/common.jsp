<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>

<script type="text/javascript">
var exceed = "<spring:message code='common.msg.exceed.possible.length'/>";
var korean = "<spring:message code='common.msg.korean'/>";
var character = "<spring:message code='common.msg.character'/>";
var englishnumber = "<spring:message code='common.msg.english.number'/>";
var writewithin = "<spring:message code='common.msg.write.within'/>";
var reloadwindow = "<spring:message code='common.msg.closeNload.window'/>";
var db_korean_byte = <%=AppConfig.getProperty("db_korean_byte", "2", "locale")%>;

<%
	boolean devmode = AppConfig.getBooleanProperty("dev_mode", false, "general");
	if (!devmode && (CommonUtil.nullTrim((String) session.getAttribute("ROLE_CODES")).indexOf(AppConfig.getProperty("role_appadmin", "", "role")) == -1)) {
%>
 		document.oncontextmenu = function() { return false; };
<%	} %>
</script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/app-common.js"></script>