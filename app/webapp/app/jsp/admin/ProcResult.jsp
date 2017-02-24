<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%
String result = CommonUtil.nullTrim((String)request.getAttribute("result"));
String message = CommonUtil.nullTrim((String)request.getAttribute("message"));
String count = CommonUtil.nullTrim((String)request.getAttribute("count"));

JSONObject json = new JSONObject();
// 처리 결과
json.put("result", result);
if (!"".equals(message)) {
    if (!"".equals(count)) {
		json.put("message", (messageSource.getMessage(message, null, langType)).replace("%s", count));
	} else {
		json.put("message", messageSource.getMessage(message, null, langType));
	}
}
if (!"".equals(count)) {
	json.put("count", count);
}
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(json.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=json.toString()%>