<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%
JSONObject json = (JSONObject)request.getAttribute("count");


logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(json.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=json.toString()%>