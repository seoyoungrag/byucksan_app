<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%
String result = (String) request.getAttribute("result");
String message = (String) request.getAttribute("message");
JSONObject json = new JSONObject();
json.put("result", result);
json.put("message", message);

if ("success".equals(result)) {
	JSONArray filelist = (JSONArray) request.getAttribute("filelist");
	json.put("filelist", filelist);
}
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(json.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");    

out.println(json.toString());
%>