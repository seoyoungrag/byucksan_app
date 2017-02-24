<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%
String result = CommonUtil.nullTrim((String) request.getAttribute("result"));
%>
<%=result%>