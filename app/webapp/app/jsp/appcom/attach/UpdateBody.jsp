<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%
String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // 본문(HTML)

String result = CommonUtil.nullTrim((String)request.getAttribute("result"));
String message = CommonUtil.nullTrim((String)request.getAttribute("message"));
JSONArray filelist = (JSONArray) request.getAttribute("filelist");
ArrayList fileidlist = (ArrayList) request.getAttribute("fileidlist");

JSONObject json = new JSONObject();
// 처리 결과
json.put("result", result);
json.put("message", message);
if (filelist != null) {
    json.put("filelist", filelist);
    if (fileidlist != null) {
		json.put("fileidlist", fileidlist);
    }
}
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(json.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=json.toString()%>