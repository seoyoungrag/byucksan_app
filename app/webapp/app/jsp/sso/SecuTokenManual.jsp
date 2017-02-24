<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="javax.swing.plaf.SliderUI"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>

<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : SecuTokenManual.jsp
 *  Description : 보안토큰 및 인증서관리 선택시 매뉴얼 화면 표시
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  S
 *  @since 2015. 07. 21 
 *  @version 1.0 
 *  @see
 */ 
%>
<% 
	String listTitle = (String) request.getAttribute("listTitle");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<script type="text/javascript">
/* 코드 구현 부분 */
		function manual_popup(imagePath){
			window.open(imagePath ,'', "width=840px , height=890px, menubar=no, scrollbars=no, status=no, directories=no, toolbar=no, resizable=no, location=no");
		}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><%=listTitle%></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
	</table >
	<div width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
		<img src="<%=webUri%>/app/ref/image/sso/menual.jpg" border="0" usemap="#Map">
		<map name="Map">
			<area shape="rect" coords="77,606,192,633" href="<%=webUri%>/app/sso/security/tokenIssueReq.do">
			<area shape="rect" coords="345,792,461,819" href="<%=webUri%>/app/sso/security/tokenIssueReq.do">
			<area shape="rect" coords="609,285,728,314" href="<%=webUri%>/app/sso/security/ListTokenManage.do">
			<area shape="rect" coords="571,400,761,456" href="javascript:manual_popup('<%=webUri%>/app/ref/image/sso/menual.jpg')">
		</map>
	</div>
</acube:outerFrame>
</body>
</html>