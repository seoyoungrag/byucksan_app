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
 *  Class Name  : TokenIssueSearch.jsp
 *  Description : ������ū ������ - �߱޴��� - �˻�
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
	
 	String msgReqUser = messageSource.getMessage("list.list.title.reqUser" , null, langType);
	String msgReqDate = messageSource.getMessage("list.list.title.reqDate1" , null, langType);
	
	// ��ư �ؽ�Ʈ ����
	String msgSearchBtn = messageSource.getMessage("list.list.button.search", null, langType);
	String msgListBtn = messageSource.getMessage("list.list.button.tokenList", null, langType);
	
	// ���� ��¥ ����
	String currentDate = DateUtil.getCurrentDay();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<script type="text/javascript">
/* �ڵ� ���� �κ� */
	
	$(document).ready(function() {
		$('#startDate').val('<%=currentDate%>');
		$('#endDate').val('<%=currentDate%>');
	});
	
	// �˻� ��ư ���ý�
	function onTokenIssueSearch() {
		if($('#reqName').val() == ''){
			alert("<spring:message code = 'list.list.alert.noInputReqName'/>");
			return false;
		}else {
			history.back();			
		}
	}
	
	// ��� ��ư ���ý�
	function onTokenIssueList() {
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
					<acube:menuButton id="tokenIssueSearchBtn" disabledid="" onclick="javascript:onTokenIssueSearch();" value='<%=msgSearchBtn%>'/>
					<acube:space between="button" />
					<acube:space between="button" />
					<acube:menuButton id="tokenIssueListBtn" disabledid="" onclick="javascript:onTokenIssueList();" value='<%=msgListBtn%>'/>
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
					<!-- ��û�� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgReqUser%></td>
						<td width="*" class="tb_left">
							<input type="text" id="reqName" name="reqName" style="width:60%; margin-bottom: 5px;">
						</td>
					</tr>
					<!-- ��û���� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgReqDate%></td>
						<td width="*" class="tb_left">
							<input type="text" id="startDate" name="startDate" style="width: 80px;" readonly="readonly"
							onclick="javascript:cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'startDate','yyyy-MM-dd');"/> ����
							<input type="hidden" id="startDateId" name="startDateId" value=""/>
							<input type="text" id="endDate" name="endDate" style="width: 80px;" readonly="readonly"
							onclick="javascript:cal.select(event, document.getElementById('endDateId'), document.getElementById('endDate'), 'endDate', 'yyyy-MM-dd');"/> ����
							<input type="hidden" id="endDateId" name="endDateId" value="">
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