<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow"
%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListPrintTop.jsp 
 *  Description : 프린트 설정 안내 화면 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 31 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
String printBtn	= messageSource.getMessage("list.list.button.print", null, langType);
String closeBtn = messageSource.getMessage("list.list.button.close", null, langType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
</head>
<body>
<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td class="ltb_left" ><b><spring:message code='list.list.msg.noticePrint1'/></b></td>
</tr>
<tr>
	<td class="ltb_left" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><spring:message code='list.list.msg.noticePrint1-1'/></b></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint2'/></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint3'/></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint4'/></td>
</tr>
<tr>
	<td class="ltb_left" > <spring:message code='list.list.msg.noticePrint5'/></td>
</tr>
<tr>
	<td class="ltb_left" >&nbsp;&nbsp;&nbsp;<spring:message code='list.list.msg.noticePrint6'/></td>
</tr>
<tr>
	<td class="ltb_right">
		<acube:buttonGroup align="right">
		<acube:menuButton id="selDept" disabledid="" onclick="javascript:printWin();return(false);" value='<%=printBtn %>' />
		<acube:space between="button" />
		<acube:menuButton id="selDept" disabledid="" onclick="javascript:closeWin();return(false);" value='<%=closeBtn %>' />
		</acube:buttonGroup>
	</td>
</tr>
</table>
</acube:outerFrame>
</body>
</html>