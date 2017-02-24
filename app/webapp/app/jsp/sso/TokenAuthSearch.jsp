<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="javax.swing.plaf.SliderUI"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"%>
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
 *  Class Name  : TokenAuthSearch.jsp
 *  Description : ������ū ������ - ������ū �������� - �˻�
 *  Modification Information 
 * 
 *   �� �� �� :  
 *   �� �� �� : 
 *   �������� : 
 * 
 *  @author  S
 *  @since 2015. 07. 21 
 *  @version 1.0 
 *  @seepadding: 10px;padding: 10px;padding: 10px;padding: 10px;
 */ 
%>
<% 
	String compId 	= (String) session.getAttribute("COMP_ID");	// ȸ�� ID
	
	String listTitle = (String) request.getAttribute("listTitle");
	
 	String msgUserName = messageSource.getMessage("list.list.title.authUserName" , null, langType);
	String msgDeptName = messageSource.getMessage("list.list.title.authDeptName" , null, langType);
	
	// ��ư �ؽ�Ʈ ����
	String msgSearchBtn = messageSource.getMessage("list.list.button.search", null, langType);
	String msgListBtn = messageSource.getMessage("list.list.button.tokenList", null, langType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<script type="text/javascript">
/* �ڵ� ���� �κ� */
	
	// �˻� ��ư ���ý�
	function onTokenAuthSearch() {
		if($('#userName').val() == ''){
			alert("<spring:message code = 'list.list.alert.noInputUserName'/>");
			return false;
		}else if ($('#deptName').val() == ''){
			alert("<spring:message code = 'list.list.alert.noInputDeptName'/>");
			return false;
		}
		history.back();
	}
	
	// ��� ��ư ���ý�
	function onTokenAuthList() {
		history.back();
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
		<!-- select �� button ���� ���� -->
 		<tr>
			<td style="padding-right: 5px;">
				<acube:buttonGroup align="right">
					<acube:menuButton id="tokenAuthSearchBtn" disabledid="" onclick="javascript:onTokenAuthSearch();" value='<%=msgSearchBtn%>'/>
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:menuButton id="tokenAuthListBtn" disabledid="" onclick="javascript:onTokenAuthList();" value='<%=msgListBtn%>'/>
				</acube:buttonGroup>
			</td>
		</tr>
		<!-- select �� button ���� ���� -->
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<form name="formSearch" id="formSearch" style="margin:0px">
			<div id="docinfo">
				<acube:tableFrame>
					<!-- ����ڸ� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgUserName%></td>
						<td width="*" class="tb_left">
							<input type="text" id="userName" name="userName" style="width:60%; margin-bottom: 5px;">
						</td>
					</tr>
					<!-- �μ��� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgDeptName%></td>
						<td width="*" class="tb_left">
							<input type="text" id="deptName" name="deptName" style="width:60%; margin-bottom: 5px;">
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			</form>
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>