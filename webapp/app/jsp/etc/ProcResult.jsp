<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%@ page import="org.codehaus.jettison.json.JSONArray"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
String result = CommonUtil.nullTrim((String)request.getAttribute("result"));
String message = CommonUtil.nullTrim((String)request.getAttribute("message"));
//String count = CommonUtil.nullTrim((String)request.getAttribute("count"));

JSONObject json = new JSONObject();
// 처리 결과
json.put("result", result);
if (!"".equals(message)) {
	json.put("message", messageSource.getMessage(message, null, langType));
}

logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(json.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=json.toString()%>