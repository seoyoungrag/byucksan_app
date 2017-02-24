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
 *  Class Name  : DiscardToken.jsp
 *  Description : ������ū���� - ��� ��ư ���� (������ū ���)
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
 	String msgManageNum = messageSource.getMessage("list.list.title.manageNum" , null, langType);
	String msgHandle = messageSource.getMessage("list.list.title.handle" , null, langType);
	String msgHandlerDept = messageSource.getMessage("list.list.title.handlerDept", null, langType);
	String msgProductName = messageSource.getMessage("list.list.title.productName", null, langType);
	String msgRegDate = messageSource.getMessage("list.relay.title.headerRegistDate", null, langType);
	String msgHandleDept = messageSource.getMessage("list.list.title.handleDept", null, langType);
	String msgDiscardReason = messageSource.getMessage("list.list.title.discardReason", null, langType);
	String msgDiscardType = messageSource.getMessage("list.list.title.discardType", null, langType);
	
	// ��ư �ؽ�Ʈ ����
	String msgSaveBtn = messageSource.getMessage("list.list.button.excelSave" , null, langType);
	String msgListBtn = messageSource.getMessage("list.list.button.tokenList" , null, langType);
	
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
	
	$(document).ready(function() {
		$('#strExpReason').val("<spring:message code = 'list.list.select.selbreakDown'/>");
	});
	
	// ���� ��ư ���ý�
	function onCertSave() {
		if($('#strExpReason').val() == ''){
			alert("<spring:message code = 'list.list.alert.tokenDelReason'/>");
			return false;
		}else {
			alert("<spring:message code = 'list.list.alert.tokenDel'/>")
			history.back();
		}
	}
	
	// ��� ��ư ���ý�
	function onCertList() {
		history.back();
	}
	
	function changeDisType(disType) {
		$('#strExpReason').val(disType);
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
					<acube:menuButton id="saveBtn" onclick="javascript:onCertSave();" value='<%=msgSaveBtn%>'/>
					<acube:space between="button" />
					<acube:menuButton id="confirmBtn" onclick="javascript:onCertList();" value='<%=msgListBtn%>'/>
					<acube:space between="button" />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<form name="formSearch" id="formSearch" style="margin:0px">
			<div id="docinfo">
				<acube:tableFrame>
					<!-- ������ȣ -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgManageNum%></td>
						<td width="*" class="tb_left">
							<div id="manageNum"></div>
						</td>
					</tr>
					
					<!-- ����� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHandle%></td>
						<td width="*" class="tb_left">
							<div id="userName"></div>
						</td>
					</tr>

					<!-- ��޺μ� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHandleDept%></td>
						<td width="*" class="tb_left">
							<div id="handleDeptId"></div>
						</td>
					</tr>
					
					<!-- ��ǰ�� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgProductName%></td>
						<td width="*" class="tb_left">
							<div id="productNmae"></div>
						</td>
					</tr>

					<!-- ������� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgRegDate%></td>
						<td width="*" class="tb_left">
							<div id="regDate"></div>
						</td>
					</tr>
					
					<!-- ������� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgDiscardType%></td>
						<td>
							<div id="discardType">
								<input type="radio" id="type1" name="discardType" value="<spring:message code = 'list.list.select.selbreakDown'/>" onclick="changeDisType(this.value);" checked="checked"> <spring:message code="list.list.select.selbreakDown"/>
								<input type="radio" id="type2" name="discardType" value="<spring:message code = 'list.list.select.selMiss'/>" onclick="changeDisType(this.value);"> <spring:message code="list.list.select.selMiss"/>
								<input type="radio" id="type3" name="discardType" value="<spring:message code = 'list.list.select.retire'/>" onclick="changeDisType(this.value);"> <spring:message code="list.list.select.retire"/>
								<input type="radio" id="type4" name="discardType" value="<spring:message code = 'list.form.etc'/>" onclick="changeDisType(this.value);"> <spring:message code="list.form.etc"/>
							</div>
						</td>
					</tr>
					
					<!-- ������ -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgDiscardReason%></td>
						<td width="*" class="tb_left">
							<div id="discardReason">
								<textarea id="strExpReason" name="strExpReason" rows="10" cols="170" TYPE="TEXT" style="background-color: #fbfbfb;"></textarea>
							</div>
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