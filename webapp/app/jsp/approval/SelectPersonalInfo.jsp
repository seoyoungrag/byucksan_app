<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.appcom.vo.SendInfoVO" %>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%@ page import="org.codehaus.jettison.json.JSONArray"%>
<%
String result = CommonUtil.nullTrim((String)request.getAttribute("result"));
SendInfoVO sendInfoVO = (SendInfoVO) request.getAttribute("sendInfoVO");

JSONObject json = new JSONObject();
// 처리 결과
if (sendInfoVO != null) {
    json.put("result", "success");
	json.put("postNumber", sendInfoVO.getPostNumber());
	json.put("address", sendInfoVO.getAddress());
	json.put("homepage", sendInfoVO.getHomepage());
	json.put("telephone", sendInfoVO.getTelephone());
	json.put("fax", sendInfoVO.getFax());
	json.put("email", sendInfoVO.getEmail());
} else {
    json.put("result", "fail");
}

logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(json.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%>
<%=json.toString()%>