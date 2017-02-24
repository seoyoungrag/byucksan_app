<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%
JSONArray filelist = (JSONArray) request.getAttribute("filelist");
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(filelist.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=filelist.toString()%>