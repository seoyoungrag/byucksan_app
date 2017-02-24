<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%
	String result     = CommonUtil.nullTrim((String)request.getAttribute("result"));
	String msg        = CommonUtil.nullTrim((String)request.getAttribute("msg"));
	String resourceId = CommonUtil.nullTrim((String)request.getAttribute("resourceId"));


	JSONObject json = new JSONObject();
	
	json.put("result", result);
	json.put("msg", msg);
	json.put("resourceId", resourceId);

	logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	logger.debug(json.toString());
	logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%= json.toString() %>