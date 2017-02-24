<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONObject"  %>
<%
JSONObject file = (JSONObject) request.getAttribute("file");

logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(file.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=file.toString()%>