<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
/** 
 *  Class Name  : adminform.jsp 
 *  Description : 관리자용 form tag 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String lobCode = CommonUtil.nullTrim((String) request.getAttribute("lobCode")); // 문서함코드
%>
<form id="managerForm" name="managerForm" method="post">
	<input id="password" name="password" type="hidden" value=""/>
	<input id="roundkey" name="roundkey" type="hidden" value=""/>
	<input id="lobCode" name="lobCode" type="hidden" value="<%=lobCode%>"/>
	<input id="docId" name="docId" type="hidden" value=""/>
	<input id="reason" name="reason" type="hidden" value=""/>
	<input id="docInfo" name="docInfo" type="hidden" value=""/>
</form>