<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%
String result = CommonUtil.nullTrim((String) request.getAttribute("result"));
JSONObject obj = new JSONObject();
obj.put("result", result);

logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(obj.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%><%=obj.toString()%>