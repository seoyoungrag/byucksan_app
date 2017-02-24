<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%
String result = CommonUtil.nullTrim((String)request.getAttribute("result"));
String message = CommonUtil.nullTrim((String)request.getAttribute("message"));
String bodypath = CommonUtil.nullTrim((String)request.getAttribute("bodypath"));
String stamppath = CommonUtil.nullTrim((String)request.getAttribute("stamppath"));
String stampwidth = CommonUtil.nullTrim((String)request.getAttribute("stampwidth"));
String stampheight = CommonUtil.nullTrim((String)request.getAttribute("stampheight"));
String processDate = CommonUtil.nullTrim((String)request.getAttribute("processDate"));

JSONObject json = new JSONObject();
// 처리 결과
json.put("result", result);
if ("success".equals(result)) {
	json.put("bodypath", bodypath);
	json.put("stamppath", stamppath);
	json.put("stampwidth", stampwidth);
	json.put("stampheight", stampheight);
	json.put("processDate", processDate);
} else {
	json.put("message", messageSource.getMessage(message, null, langType));
}
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(json.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=json.toString()%>