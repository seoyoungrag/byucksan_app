<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="org.codehaus.jettison.json.JSONObject"  %>
<%
/** 
 *  Class Name  : ProcResultOption.jsp 
 *  Description : 옵션 처리결과 공통 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.11 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 11 
 *  @version 1.0 
 */
%>
<%
	String msg = messageSource.getMessage((String)request.getAttribute("msg") , null, langType);
	String url = (String)request.getAttribute("url");
	String conditionValue = CommonUtil.nullTrim((String)request.getAttribute("conditionValue"));

	JSONObject obj = new JSONObject();
	obj.put("msg", msg);
	obj.put("url", webUri+url);
	obj.put("conditionValue", conditionValue);
	logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	logger.debug(obj.toString());
	logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	%>
	<%=obj.toString()%>

