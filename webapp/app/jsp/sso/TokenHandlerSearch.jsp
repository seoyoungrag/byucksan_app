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
 *  Class Name  : ModifyTokenInfo.jsp
 *  Description : ������ū ������ - ��������(��������)
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
	String roleCodes = (String) session.getAttribute("ROLE_CODES"); // ���� CODE
	
	String listTitle = (String) request.getAttribute("listTitle");
 	String msgNameAndNum = messageSource.getMessage("list.list.title.nameandnum" , null, langType);
	String msgSubName = messageSource.getMessage("list.list.title.subName" , null, langType);
	String msgSubDept = messageSource.getMessage("list.etc.left.subDept" , null, langType);
	
	// ��ư ����
	String msgSearchBtn = messageSource.getMessage("list.list.button.search" , null, langType);
	String msgCloseBtn = messageSource.getMessage("list.list.button.close" , null, langType);
	
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); //ó���� ����å����
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String userName = userProfileVO.getUserName();
	
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
	
	// ���� / ��� �Է� �κ� Ŭ����
	function onInputHandler() {
		$('#userInfo').val('');
	}
	
	// �˻� ��ư ���ý�
	function onHandlerSearch() {
		window.close();
	}
	
	// ��� ��ư ���ý�
	function onCertList() {
		window.close();
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
		<tr>
			<td>
				<acube:buttonGroup align="right">
					<acube:menuButton id="modifyBtn" onclick="javascript:onHandlerSearch();" value='<%=msgSearchBtn%>'/>
					<acube:space between="button" />
					<acube:menuButton id="closeBtn" onclick="javascript:onCertList();" value='<%=msgCloseBtn%>'/>
					<acube:space between="button" />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<div id="docinfo">
				<acube:tableFrame>
					<!-- ���� / ��� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgNameAndNum%></td>
						<td width="*" class="tb_left">
							<input type="text" id="userInfo" name="userInfo" title="<spring:message code = 'list.list.title.haldlerInputHint'/>" value="<spring:message code = 'list.list.title.haldlerInputHint'/>" onclick="javascript:onInputHandler();" style="width: 95%; margin-bottom: 5px;">
						</td>
					</tr>
					<!-- ���� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgSubName%></td>
						<td width="*" class="tb_left">
							<div id="userName"></div>
						</td>
					</tr>
					<!-- �μ� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgSubDept%></td>
						<td width="*" class="tb_left">
							<div id="deptName"></div>
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>