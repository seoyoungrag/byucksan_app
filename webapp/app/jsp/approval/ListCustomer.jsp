<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.approval.vo.CustomerVO" %>
<%@ page import="org.codehaus.jettison.json.JSONObject"%>
<%@ page import="org.codehaus.jettison.json.JSONArray"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
String result = CommonUtil.nullTrim((String)request.getAttribute("result"));
String message = CommonUtil.nullTrim((String)request.getAttribute("message"));
String count = CommonUtil.nullTrim((String)request.getAttribute("count"));
List<CustomerVO> customerVOs = (List<CustomerVO>) request.getAttribute("customerVOs");

// 처리 데이터
JSONArray jsonArray = new JSONArray();		
if (customerVOs != null) {
    int customerCount = customerVOs.size();
	for (int loop = 0; loop < customerCount; loop++) {
	    CustomerVO customerVO = customerVOs.get(loop);
		JSONObject json = new JSONObject();
		json.put("customerId", CommonUtil.nullTrim(customerVO.getCustomerId()));
		json.put("customerName", CommonUtil.nullTrim(customerVO.getCustomerName()));
	    jsonArray.put(json);
	}

	logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	logger.debug(jsonArray.toString());
	logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
}
%>
<%=jsonArray.toString()%>
