<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONObject" %><%
/** 
 *  Class Name  : EnvFormResult.jsp 
 *  Description : 발송테스트 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.21  
 *   수 정 자 : 윤동원 
 *   수정내용 : 수정
 * 
 *  @author  윤동원
 *  @since 2011. 3. 21 
 *  @version 1.0 
 */ 
%><%
String result = CommonUtil.nullTrim((String) request.getAttribute("result"));
JSONObject obj = new JSONObject();
obj.put("result", result);

logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
logger.debug(obj.toString());
logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
%><%=obj.toString()%>